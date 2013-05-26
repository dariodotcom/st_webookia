package it.webookia.backend.controller.rest;

import it.webookia.backend.controller.resources.LoanResource;
import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceErrorType;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.controller.rest.responses.ResponseFactory;
import it.webookia.backend.utils.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
public class LoanRest {

    private String loanId;
    private String requestorUsername;

    public LoanRest(String id, HttpServletRequest req) {
        super();
        this.loanId = id;
        this.requestorUsername = ServletUtils.getAuthenticatedUsername(req);
    }

    @GET
    public Response doGet() {
        try {
            return ResponseFactory.createFrom(getContextLoan().getDescriptor());
        } catch (ResourceException e) {
            return ResponseFactory.createFrom(e);
        }
    }

    // Status handling
    @GET
    @Path("accept")
    public Response accept() {
        return takeLoanDecision(true);
    }

    @GET
    @Path("refuse")
    public Response refuse() {
        return takeLoanDecision(false);
    }

    @GET
    @Path("shipped")
    public Response shipped() {
        try {
            LoanResource loan = getContextLoan();
            loan.bookReceived(getRequestor());
            return ResponseFactory.createFrom(loan.getDescriptor());
        } catch (ResourceException e) {
            return ResponseFactory.createFrom(e);
        }
    }

    @GET
    @Path("giveback")
    public Response givenBack() {
        try {
            LoanResource loan = getContextLoan();
            loan.bookReturned(getRequestor());
            return ResponseFactory.createFrom(loan.getDescriptor());
        } catch (ResourceException e) {
            return ResponseFactory.createFrom(e);
        }
    }

    // Messages

    // Helpers
    private UserResource getRequestor() throws ResourceException {
        if (requestorUsername == null) {
            throw new ResourceException(
                ResourceErrorType.UNAUTHORIZED_ACTION,
                "login required");
        }
        return UserResource.getUser(requestorUsername);
    }

    private LoanResource getContextLoan() throws ResourceException {
        return LoanResource.getLoan(getRequestor(), loanId);
    }

    private Response takeLoanDecision(boolean decision) {
        try {
            LoanResource loan = getContextLoan();
            loan.respond(getRequestor(), decision);
            return ResponseFactory.createFrom(loan.getDescriptor());
        } catch (ResourceException e) {
            return ResponseFactory.createFrom(e);
        }
    }
}
