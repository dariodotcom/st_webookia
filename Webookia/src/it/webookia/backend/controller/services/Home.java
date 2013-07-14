package it.webookia.backend.controller.services;

import it.webookia.backend.controller.services.impl.Jsp;
import it.webookia.backend.controller.services.impl.Service;
import it.webookia.backend.controller.services.impl.ServiceContext;
import it.webookia.backend.controller.services.impl.ServiceServlet;
import it.webookia.backend.controller.services.impl.Verb;
import it.webookia.backend.utils.servlets.Context;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 * This is the class which manages pages reached from the landing page,
 * implementing {@service} with {@HomeService} and
 * {@PrivacyService}
 * 
 */
public class Home extends ServiceServlet {

    private static final long serialVersionUID = 6199421201754122052L;

    /**
     * Class constructor
     */
    public Home() {
        super(Context.HOME);
        registerDefaultService(Verb.GET, new HomeService());
        registerService(Verb.GET, "privacy", new PrivacyService());
    }

    /**
     * This class manages to reach user's personal home page or welcome page in
     * case of unauthencticated user or after a logout.
     * 
     */
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

    /**
     * This class manages to reach the page containing the privacy notes,
     * according to Facebook policies.
     * 
     */
    private class PrivacyService implements Service {
        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {

            context.forwardToJsp(Jsp.PRIVACY_JSP);

        }
    }

}
