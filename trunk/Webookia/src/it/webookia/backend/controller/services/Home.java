package it.webookia.backend.controller.services;

import it.webookia.backend.controller.services.impl.Jsp;
import it.webookia.backend.controller.services.impl.Service;
import it.webookia.backend.controller.services.impl.ServiceContext;
import it.webookia.backend.controller.services.impl.ServiceServlet;
import it.webookia.backend.controller.services.impl.Verb;
import it.webookia.backend.utils.servlets.Context;

import java.io.IOException;

import javax.servlet.ServletException;

public class Home extends ServiceServlet {

    private static final long serialVersionUID = 6199421201754122052L;

    public Home() {
        super(Context.HOME);
        registerDefaultService(Verb.GET, new HomeService());
        registerService(Verb.GET, "privacy", new PrivacyService());
    }

    private class HomeService implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {

            if (context.isUserLoggedIn()) {
                new Users.SelfProfileService().service(context);
            } else {
                context.forwardToJsp(Jsp.WELCOME_JSP);
            }
        }

    }

    private class PrivacyService implements Service {
        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {

            context.forwardToJsp(Jsp.PRIVACY_JSP);

        }
    }

}
