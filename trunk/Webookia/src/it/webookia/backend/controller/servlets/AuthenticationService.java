package it.webookia.backend.controller.servlets;

import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.utils.foreignws.facebook.AccessToken;
import it.webookia.backend.utils.foreignws.facebook.FacebookConnector;
import it.webookia.backend.utils.foreignws.facebook.OAuthException;

import java.io.IOException;

import javax.servlet.ServletException;

public class AuthenticationService extends ServiceServlet {

    private static final long serialVersionUID = 4703628228514306116L;

    public AuthenticationService() {
        super("authentication");
        super.registerService(Verb.GET, "landing", new LoginLanding());
    }

    private class LoginLanding implements Service {
        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {

            String code = context.getParameter("code");
            String error = context.getParameter("error");
            String userId = context.getAuthenticatedUserId();

            if (userId != null) {
                context.getResponse().sendRedirect("/home/");
            } else if (code != null) {
                AccessToken token;
                try {
                    token = FacebookConnector.performOauthValidation(code);
                } catch (OAuthException o) {
                    throw new ServletException(o);
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
}
