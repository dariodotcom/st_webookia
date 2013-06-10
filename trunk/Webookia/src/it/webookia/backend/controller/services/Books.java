package it.webookia.backend.controller.services;

import it.webookia.backend.controller.resources.BookResource;
import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.controller.services.impl.Jsp;
import it.webookia.backend.controller.services.impl.Service;
import it.webookia.backend.controller.services.impl.ServiceContext;
import it.webookia.backend.controller.services.impl.ServiceServlet;
import it.webookia.backend.controller.services.impl.Verb;
import it.webookia.backend.utils.servlets.Context;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 * 
 * This is the servlet which manages the books, allowing the creation, search,
 * detail.
 * 
 */
public class Books extends ServiceServlet {

    private static final long serialVersionUID = -7169462947426225834L;

    public static final String CONTEXT_BOOK = "CONTEXT_BOOK";

    public static final String BOOKLIST_ATTR = "BOOKLIST";

    /**
     * This is the constructor of the class, depending on the user invoked
     * service.
     */
    public Books() {
        super(Context.BOOKS);
        registerDefaultService(Verb.GET, new BookLanding());
        registerService(Verb.POST, "create", new BookCreation());
        registerService(Verb.GET, "search", new BookSearch());
        registerService(Verb.GET, "detail", new BookDetail());
    }

    public class BookLanding implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            String userId = context.getAuthenticatedUserId();
            UserResource user;

            try {
                user = UserResource.getUser(userId);
            } catch (ResourceException e) {
                context.sendError(e);
                return;
            }

            context.setRequestAttribute(BOOKLIST_ATTR, user.getUserBooks());
            context.forwardToJsp(Jsp.BOOK_JSP);
        }
    }

    /**
     * This class sets the details of a ConcreteBook, using the book isbn and
     * the user identifier. Leveraging this informations an external service
     * will provide all the other missing informations about the book.
     * 
     */
    public class BookDetail implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {

            String bookId = context.getRequestParameter("id");
            String requestorId = context.getAuthenticatedUserId();

            try {
                UserResource requestor = UserResource.getUser(requestorId);
                BookResource book = BookResource.getBook(bookId, requestor);
                context.setRequestAttribute(CONTEXT_BOOK, book);
                context.forwardToJsp(Jsp.BOOK_DETAIL_JSP);
            } catch (ResourceException e) {
                context.sendError(e);
                return;
            }
        }
    }

    /**
     * 
     * This class manages the search of a book, depending on its isbn either its
     * title or its author.
     * 
     */
    public class BookSearch implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            // TODO Auto-generated method stub

        }
    }

    /**
     * 
     * This class creates a book , taking as information its isbn and owner and
     * redirects the user to the book page.
     * 
     */
    public class BookCreation implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            String isbn = context.getRequestParameter("isbn");
            String user = context.getAuthenticatedUserId();

            try {

                UserResource requestor = UserResource.getUser(user);
                BookResource book = BookResource.createBook(isbn, requestor);
                String id = book.getDescriptor().getId();
                String newUrl = "/books/detail?id=" + id;
                context.sendRedirect(newUrl);

            } catch (ResourceException e) {
                context.sendError(e);
                return;
            }

        }
    }

}
