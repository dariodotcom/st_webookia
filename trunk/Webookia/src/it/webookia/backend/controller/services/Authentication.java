package it.webookia.backend.controller.services;

import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.services.impl.Service;
import it.webookia.backend.controller.services.impl.ServiceContext;
import it.webookia.backend.controller.services.impl.ServiceServlet;
import it.webookia.backend.controller.services.impl.Verb;
import it.webookia.backend.utils.foreignws.facebook.AccessToken;
import it.webookia.backend.utils.foreignws.facebook.FacebookConnector;
import it.webookia.backend.utils.foreignws.facebook.OAuthException;

import java.io.IOException;

import javax.servlet.ServletException;

public class Authentication extends ServiceServlet {

    private static final long serialVersionUID = 4703628228514306116L;

    public Authentication() {
        super("authentication");
        super.registerService(Verb.GET, "login", new LoginService());
        super.registerService(Verb.GET, "logout", new LogoutService());
    }

    private class LoginService implements Service {
        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {

            String code = context.getRequestParameter("code");
            String error = context.getRequestParameter("error");
            String userId = context.getAuthenticatedUserId();

            if (userId != null) {
                context.getResponse().sendRedirect("/home/");
            } else if (code != null) {
                AccessToken token;
                try {
                    token = FacebookConnector.performOauthValidation(code);
                } catch (OAuthException o) {
                    throw new ServletException(o); // TODO Converto into a
                                                   // Resource Exception
                }

                UserResource userRes = UserResource.authenticateUser(token);
                context.setAuthenticatedUserId(userRes.getUserId());
                context.getResponse().sendRedirect("/home/");

            } else if (error != null) {
                throw new ServletException("Error from oauth dialog: " + error);
            } else {
                throw new ServletException("Not a response from facebook");
            }
        }
    }

    private class LogoutService implements Service {
        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            context.setAuthenticatedUserId(null);
            context.sendRedirect("/home/");
        }
    }
}
