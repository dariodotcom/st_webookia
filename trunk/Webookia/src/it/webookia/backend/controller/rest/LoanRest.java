package it.webookia.backend.controller.rest;

import it.webookia.backend.controller.resources.LoanResource;
import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceErrorType;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.controller.rest.requests.FeedbackAdditionRequest;
import it.webookia.backend.controller.rest.requests.MessageRequest;
import it.webookia.backend.controller.rest.responses.ResponseFactory;
import it.webookia.backend.utils.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    @Path("messages")
    public LoanMessages loanMessages() {
        return new LoanMessages();
    }

    // Feedback
    @Path("feedback")
    public LoanFeedback loanFeedback() {
        return new LoanFeedback();
    }

    @Produces(MediaType.APPLICATION_JSON)
    public class LoanFeedback {
        @GET
        public Response get() {
            try {
                LoanResource loan = getContextLoan();
                return ResponseFactory.createFrom(loan.getFeedbacks());
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }

        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        public Response post(FeedbackAdditionRequest feedbackRequest) {
            try {
                LoanResource loan = getContextLoan();
                loan.addFeedback(
                    getRequestor(),
                    feedbackRequest.getMark(),
                    feedbackRequest.getText());
                return ResponseFactory.createFrom(loan.getDescriptor());
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }
    }

    // Private classes
    @Produces(MediaType.APPLICATION_JSON)
    public class LoanMessages {

        @GET
        public Response get() {
            try {
                return ResponseFactory.createFrom(getContextLoan()
                    .getMessages());
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }

        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        public Response post(MessageRequest messageRequest) {
            try {
                LoanResource loan = getContextLoan();
                UserResource requestor = getRequestor();
                loan.sendContextMessage(
                    requestor,
                    messageRequest.getMessageText());
                return ResponseFactory.createFrom(loan.getMessages());
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }
    }

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