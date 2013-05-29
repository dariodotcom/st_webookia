package it.webookia.backend.controller.rest;

import it.webookia.backend.controller.resources.BookResource;
import it.webookia.backend.controller.resources.LoanResource;
import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.controller.rest.requests.LoanCreationRequest;
import it.webookia.backend.controller.rest.responses.ResponseFactory;
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

@Path("loans")
public class LoanContext {

    @Context
    private HttpServletRequest request;

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLoan(LoanCreationRequest creationRequest) {
        String borrowerUsername =
            ServletUtils.getAuthenticatedUserId(request);
        UserResource borrower;
        BookResource book;

        try {
            borrower = UserResource.getUser(borrowerUsername);
            book = BookResource.getBook(creationRequest.getBookId(), borrower);
            LoanResource loan = LoanResource.createLoan(borrower, book);
            return ResponseFactory.createFrom(loan.getDescriptor());
        } catch (ResourceException e) {
            return ResponseFactory.createFrom(e);
        }

    }

    @Path("loan/{id}")
    public LoanRest handleLoan(@PathParam(value = "id") String id) {
        return new LoanRest(id, request);
    }
}
