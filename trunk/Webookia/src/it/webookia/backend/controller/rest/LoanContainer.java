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
public class LoanContainer {

    private HttpServletRequest request;

    public LoanContainer(@Context HttpServletRequest request) {
        this.request = request;
    }

    @POST
    @Path("new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLoan(LoanCreationRequest creationRequest) {

        System.out.println("attr: "
            + request.getSession().getAttribute("AUTH_USER"));

        String requestorId = ServletUtils.getAuthenticatedUserId(request);
        String bookId = creationRequest.getBookId();

        System.out.println(requestorId + " asked for " + bookId);

        try {
            UserResource requestor = UserResource.getUser(requestorId);
            BookResource book = BookResource.getBook(bookId, requestor);
            LoanResource Loan = LoanResource.createLoan(requestor, book);
            return ResponseFactory.createFrom(Loan.getDescriptor());
        } catch (ResourceException e) {
            return ResponseFactory.createFrom(e);
        }
    }

    @Path("loan/{id}")
    public LoanRest handleLoanRequest(@PathParam(value = "id") String id) {
        return new LoanRest(id, request);
    }

}
