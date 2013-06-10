package it.webookia.backend.controller.services;

import java.io.IOException;

import javax.servlet.ServletException;

import it.webookia.backend.controller.resources.BookResource;
import it.webookia.backend.controller.resources.LoanResource;
import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.controller.services.impl.Jsp;
import it.webookia.backend.controller.services.impl.Service;
import it.webookia.backend.controller.services.impl.ServiceContext;
import it.webookia.backend.controller.services.impl.ServiceServlet;
import it.webookia.backend.controller.services.impl.Verb;

public class Loans extends ServiceServlet {

    private static final long serialVersionUID = 2335276930451134433L;

    public static final String CONTEXT_LOAN_ID = "id";
    public static final String CONTEXT_LOAN = "CONTEXT_LOAN";
    public static final String SENT_LOANS = "SENT_LOANS";
    public static final String RECEIVED_LOANS = "RECEIVED_LOANS";

    public Loans() {
        super("loans");
        registerDefaultService(Verb.GET, new LoanLanding());
        registerService(Verb.POST, "create", new LoanCreation());
        registerService(Verb.GET, "detail", new LoanDetail());
    }

    public class LoanCreation implements Service {
        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {

            String bookId = null;
            String userId = context.getAuthenticatedUserId();

            try {
                UserResource requestor = UserResource.getUser(userId);
                BookResource book = BookResource.getBook(bookId, requestor);
                LoanResource loan = LoanResource.createLoan(requestor, book);
                String loanId = loan.getDescriptor().getId();
                String newUrl = "/loan/detail?id=" + loanId;
                context.sendRedirect(newUrl);
            } catch (ResourceException e) {
                context.sendError(e);
                return;
            }
        }
    }

    public class LoanLanding implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            String userId = context.getAuthenticatedUserId();

            try {
                UserResource user = UserResource.getUser(userId);
                context.setRequestAttribute(SENT_LOANS, user
                    .getSentLoanRequest(-1)
                    .getList());
                context.setRequestAttribute(RECEIVED_LOANS, user
                    .getReceivedLoanRequest(-1)
                    .getList());
                context.forwardToJsp(Jsp.LOAN_JSP);
            } catch (ResourceException e) {
                context.sendError(e);
            }

        }
    }

    public class LoanDetail implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            String loanId = context.getRequestParameter(CONTEXT_LOAN_ID);
            String requestorId = context.getAuthenticatedUserId();
            try {
                UserResource requestor = UserResource.getUser(requestorId);
                LoanResource loan = LoanResource.getLoan(requestor, loanId);
                context.setRequestAttribute(CONTEXT_LOAN, loan);
                context.forwardToJsp(Jsp.LOAN_DETAIL);
            } catch (ResourceException e) {
                context.sendError(e);
            }
        }
    }

    // richiesta prestito
    // loan utente
}
