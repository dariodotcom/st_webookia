package it.webookia.backend.controller.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.webookia.backend.controller.resources.BookResource;
import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.controller.rest.requests.BookUpdateRequest;
import it.webookia.backend.controller.rest.requests.CommentCreationRequest;
import it.webookia.backend.controller.rest.requests.ReviewCreationRequest;
import it.webookia.backend.controller.rest.responses.ResponseFactory;
import it.webookia.backend.descriptor.ReviewDescriptor;
import it.webookia.backend.utils.ServletUtils;

@Produces("application/json")
public class BookRest {

    private String id;
    private HttpServletRequest request;

    public BookRest(String id, HttpServletRequest request) {
        this.id = id;
        this.request = request;
    }

    @GET
    public Response doGet() {
        try {
            BookResource book = getContextBook();
            return ResponseFactory.createFrom(book.getDescriptor());
        } catch (ResourceException e) {
            return ResponseFactory.createFrom(e);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doPost(BookUpdateRequest req) {
        try {
            BookResource book = getContextBook();
            if (req.getPrivacy() != null) {
                book.changePrivacy(req.getPrivacy(), getRequestor());
            }

            if (req.getStatus() != null) {
                book.changeStatus(req.getStatus(), getRequestor());
            }
            return ResponseFactory.createFrom(book.getDescriptor());
        } catch (ResourceException e) {
            return ResponseFactory.createFrom(e);
        }
    }

    @Path("review")
    public ReviewRest handleReview() {
        return new ReviewRest();
    }

    @Produces(MediaType.APPLICATION_JSON)
    public class ReviewRest {

        @GET
        public Response getReview() {
            try {
                BookResource book = getContextBook();
                ReviewDescriptor descriptor = book.getReview();
                return ResponseFactory.createFrom(descriptor);
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }

        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        public Response postReview(ReviewCreationRequest reviewReq) {
            try {
                BookResource book = getContextBook();
                UserResource requestor = getRequestor();
                book.addReview(
                    requestor,
                    reviewReq.getText(),
                    reviewReq.getMark());
                return ResponseFactory.createFrom(book.getReview());
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }

        @Path("comment")
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        public Response postComment(CommentCreationRequest commentReq) {
            try {
                BookResource book = getContextBook();
                UserResource requestor = getRequestor();
                book.addComment(requestor, commentReq.getText());
                return ResponseFactory.createFrom(book.getReview());
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }
    }

    private UserResource getRequestor() throws ResourceException {
        String authUserName = ServletUtils.getAuthenticatedUserId(request);
        return UserResource.getUser(authUserName);
    }

    private BookResource getContextBook() throws ResourceException {
        return BookResource.getBook(id, getRequestor());
    }
}
