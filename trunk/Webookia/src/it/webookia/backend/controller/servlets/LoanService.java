package it.webookia.backend.controller.servlets;

import java.io.IOException;

import javax.servlet.ServletException;

import it.webookia.backend.controller.resources.BookResource;
import it.webookia.backend.controller.resources.LoanResource;
import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceException;

public class LoanService extends ServiceServlet {

    private static final long serialVersionUID = 2335276930451134433L;

    public LoanService() {
        super("loan");
        registerService(Verb.POST, "create", new LoanCreation());
    }

    public class LoanCreation implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            
            String bookId = context.getConcreteBookId();
            String userId = context.getAuthenticatedUserId();
            
            try {
                UserResource requestor = UserResource.getUser(userId);
                BookResource book = BookResource.getBook(bookId, requestor);
                LoanResource loan = LoanResource.createLoan(requestor, book);
                String loanId = loan.getDescriptor().getId();
                String newUrl = "/loan/detail?id="+loanId;
                context.sendRedirect(newUrl);
            } catch (ResourceException e) {
                context.sendError(e);
                return;
            }

        }

    }

    // richiesta prestito
    // loan utente
}
