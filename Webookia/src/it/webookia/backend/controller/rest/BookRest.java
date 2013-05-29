package it.webookia.backend.controller.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.webookia.backend.controller.resources.BookResource;
import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.controller.rest.requests.BookUpdateRequest;
import it.webookia.backend.controller.rest.responses.ResponseFactory;
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
                book.changePrivacy(req.getPrivacy());
            }

            if (req.getStatus() != null) {
                book.changeStatus(req.getStatus());
            }
            return ResponseFactory.createFrom(book.getDescriptor());
        } catch (ResourceException e) {
            return ResponseFactory.createFrom(e);
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
