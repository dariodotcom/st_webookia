package it.webookia.backend.controller.servlets;

import it.webookia.backend.controller.resources.BookResource;
import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.descriptor.BookDescriptor;

import java.io.IOException;

import javax.servlet.ServletException;

public class BookService extends ServiceServlet {

    private static final long serialVersionUID = -7169462947426225834L;

    public static final String CONTEXT_BOOK = "CONTEXT_BOOK";

    public BookService() {
        super("books");
        registerService(Verb.POST, "create", new BookCreation());
        registerService(Verb.GET, "search", new BookSearch());
        registerService(Verb.GET, "detail", new BookSearch());
        registerService(Verb.GET, "detail", new BookSearch());
    }

    public class BookDetail implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {

            String bookId = context.getRequestParameter("id");
            String requestorId = context.getAuthenticatedUserId();

            try {
                UserResource requestor = UserResource.getUser(requestorId);
                BookResource book = BookResource.getBook(bookId, requestor);
                BookDescriptor descriptor = book.getDescriptor();
                context.setRequestAttribute(CONTEXT_BOOK, descriptor);
                context.forwardToJsp(Jsp.BOOK_JSP);
            } catch (ResourceException e) {
                context.sendError(e);
                return;
            }
        }
    }

    public class BookSearch implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            // TODO Auto-generated method stub

        }
    }

    public class BookCreation implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {

        }
    }

}
