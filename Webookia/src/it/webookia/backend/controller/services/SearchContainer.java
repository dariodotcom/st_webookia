package it.webookia.backend.controller.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import it.webookia.backend.controller.resources.BookResource;
import it.webookia.backend.controller.services.impl.Jsp;
import it.webookia.backend.controller.services.impl.Service;
import it.webookia.backend.controller.services.impl.ServiceContext;
import it.webookia.backend.controller.services.impl.ServiceServlet;
import it.webookia.backend.controller.services.impl.Verb;
import it.webookia.backend.descriptor.BookDescriptor;
import it.webookia.backend.utils.servlets.Context;
import it.webookia.backend.utils.servlets.SearchParameters;

public class SearchContainer extends ServiceServlet {

    private static final long serialVersionUID = -2990363915560523870L;

    public SearchContainer() {
        super(Context.SEARCH);
        registerService(Verb.POST, "result", new SearchService());
        registerDefaultService(Verb.GET, new SearchLanding());
    }

    public static class SearchLanding implements Service {
        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {

            context.forwardToJsp(Jsp.SEARCH_JSP);
        }
    }

    public static class SearchService implements Service {
        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            
            SearchParameters params = SearchParameters.createFrom(context.getRequest());
//            List<BookDescriptor> results = BookResource.lookUp(params);
            
            context.forwardToJsp(Jsp.SEARCH_JSP);
        }
    }
}
