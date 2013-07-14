package it.webookia.backend.controller.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import it.webookia.backend.controller.resources.BookResource;
import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.controller.services.impl.Jsp;
import it.webookia.backend.controller.services.impl.Service;
import it.webookia.backend.controller.services.impl.ServiceContext;
import it.webookia.backend.controller.services.impl.ServiceServlet;
import it.webookia.backend.controller.services.impl.Verb;
import it.webookia.backend.descriptor.BookDescriptor;
import it.webookia.backend.descriptor.DetailedBookDescriptor;
import it.webookia.backend.descriptor.ListDescriptor;
import it.webookia.backend.utils.servlets.Context;
import it.webookia.backend.utils.servlets.SearchParameters;

/**
 * 
 * This is the servlet which manages the research of a book.
 * 
 */
public class SearchContainer extends ServiceServlet {

    private static final long serialVersionUID = -2990363915560523870L;

    public static final String CONCRETE_RESULTS = "CONCRETE_RESULTS";
    public static final String DETAILED_RESULTS = "DETAILED_RESULTS";
    public static final String PARAMETERS = "PARAMETERS";

    /**
     * Class constructor
     */
    public SearchContainer() {
        super(Context.SEARCH);
        registerService(Verb.POST, "result", new SearchDetail());
        registerService(Verb.GET, "concrete", new SearchInstances());
        registerDefaultService(Verb.GET, new SearchLanding());
    }

    /**
     * This service redirects the user to the research page.
     * 
     */
    public static class SearchLanding implements Service {
        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            context.forwardToJsp(Jsp.SEARCH_JSP);
        }
    }

    /**
     * This service retrieves all the possible detailed books corresponding to
     * the inserted parameters. In this way a user can choose the result he was
     * meant to.
     * 
     */
    public static class SearchDetail implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            SearchParameters params =
                SearchParameters.createFrom(context.getRequest());

            ListDescriptor<DetailedBookDescriptor> results =
                BookResource.lookupDetailedBooks(params);
            context.setRequestAttribute(
                DETAILED_RESULTS,
                new DetailedResultBox(results));
            context.setRequestAttribute(PARAMETERS, params);

            context.forwardToJsp(Jsp.SEARCH_JSP);

        }
    }

    /**
     * This service retrieves all the possible concrete books corresponding to
     * the chosen detailed book.
     * 
     */
    public static class SearchInstances implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            String bookId = context.getRequestParameter("detail");
            ListDescriptor<BookDescriptor> result;

            String userId = context.getAuthenticatedUserId();
            UserResource user;

            try {
                user = UserResource.getUser(userId);
                result = BookResource.lookupConcreteBooks(bookId, user);
            } catch (ResourceException e) {
                context.sendError(e);
                return;
            }

            context.setRequestAttribute(
                CONCRETE_RESULTS,
                new ConcreteResultBox(result));
            context.forwardToJsp(Jsp.CONCRETE_JSP);
        }
    }

    /**
     * This class retrieves the methods to manage results relative to detailed
     * books.
     * 
     */
    public static class DetailedResultBox {
        private List<DetailedBookDescriptor> list;

        /**
         * Class constructor.
         * 
         * @param desc
         */
        public DetailedResultBox(ListDescriptor<DetailedBookDescriptor> desc) {
            this.list = desc.getList();
        }

        // Getter
        public List<DetailedBookDescriptor> getList() {
            return list;
        }
    }

    /**
     * This class retrieves the methods to manage results relative to concrete
     * books.
     * 
     */
    public static class ConcreteResultBox {
        private List<BookDescriptor> list;

        /**
         * Class constructor
         * 
         * @param desc
         */
        public ConcreteResultBox(ListDescriptor<BookDescriptor> desc) {
            this.list = desc.getList();
        }

        // Getter
        public List<BookDescriptor> getList() {
            return list;
        }
    }
}
