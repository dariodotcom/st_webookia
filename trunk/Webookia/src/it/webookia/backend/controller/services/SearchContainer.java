package it.webookia.backend.controller.services;

import java.io.IOException;

import javax.servlet.ServletException;

import it.webookia.backend.controller.resources.BookResource;
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

public class SearchContainer extends ServiceServlet {

    private static final long serialVersionUID = -2990363915560523870L;

    public static final String CONCRETE_RESULTS = "CONCRETE_RESULTS";
    public static final String DETAILED_RESULTS = "DETAILED_RESULTS";

    public SearchContainer() {
        super(Context.SEARCH);
        registerService(Verb.POST, "result", new SearchDetail());
        registerService(Verb.GET, "instances", new SearchInstances());
        registerDefaultService(Verb.GET, new SearchLanding());
    }

    public static class SearchLanding implements Service {
        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {

            context.forwardToJsp(Jsp.SEARCH_JSP);
        }
    }

    public static class SearchDetail implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {

            SearchParameters params =
                SearchParameters.createFrom(context.getRequest());

            ListDescriptor<DetailedBookDescriptor> results =
                BookResource.lookupDetailedBooks(params);
            context.setRequestAttribute(DETAILED_RESULTS, results);
            context.forwardToJsp(Jsp.SEARCH_JSP);

        }
    }

    public static class SearchInstances implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            String id = context.getRequestParameter("detail");
            ListDescriptor<BookDescriptor> result;

            try {
                result = BookResource.lookupConcreteBooks(id);
            } catch (ResourceException e) {
                context.sendError(e);
                return;
            }

            context.setRequestAttribute(CONCRETE_RESULTS, result);
            context.forwardToJsp(Jsp.SEARCH_JSP);
        }
    }
}
