package it.webookia.backend.controller.rest;

import it.webookia.backend.controller.resources.BookResource;
import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceErrorType;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.controller.rest.requests.BookCreationRequest;
import it.webookia.backend.controller.rest.responses.ResponseFactory;
import it.webookia.backend.enums.PrivacyLevel;
import it.webookia.backend.utils.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("books")
public class BookContext {

    @Context
    HttpServletRequest request;

    @POST
    @Path("new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(BookCreationRequest creationRequest) {

        String isbn = creationRequest.getIsbn();
        String authUserName = ServletUtils.getAuthenticatedUsername(request);

        ResourceException ex = null;

        if (isbn == null) {
            ex = new ResourceException(ResourceErrorType.BAD_REQUEST);
        } else if (authUserName == null) {
            ex = new ResourceException(ResourceErrorType.UNAUTHORIZED_ACTION);
        }

        if (ex != null) {
            return ResponseFactory.createFrom(ex);
        }

        try {
            UserResource requestor = UserResource.getUser(authUserName);
            BookResource book = BookResource.createBook(isbn, requestor);
            PrivacyLevel privacy = creationRequest.getPrivacy();
            if (privacy != null) {
                book.changePrivacy(privacy);
            }

            return ResponseFactory.createFrom(book.getDescriptor());
        } catch (ResourceException e) {
            return ResponseFactory.createFrom(e);
        }
    }

    @Path("book/{id}")
    public BookRest handleBookRequest(@PathParam(value = "id") String id) {
        return new BookRest(id, request);
    }
}
