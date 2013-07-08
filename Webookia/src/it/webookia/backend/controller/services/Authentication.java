package it.webookia.backend.controller.services;

import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceErrorType;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.controller.services.impl.Service;
import it.webookia.backend.controller.services.impl.ServiceContext;
import it.webookia.backend.controller.services.impl.ServiceServlet;
import it.webookia.backend.controller.services.impl.Verb;
import it.webookia.backend.utils.foreignws.facebook.AccessToken;
import it.webookia.backend.utils.foreignws.facebook.FacebookConnector;
import it.webookia.backend.utils.foreignws.facebook.FacebookConnectorException;
import it.webookia.backend.utils.servlets.Context;

import java.io.IOException;

import javax.servlet.ServletException;

public class Authentication extends ServiceServlet {

    private static final long serialVersionUID = 4703628228514306116L;

    public Authentication() {
        super(Context.AUTHENTICATION);
        super.registerService(Verb.GET, "login", new LoginService());
        super.registerService(Verb.GET, "logout", new LogoutService());
    }

    /**
     * User login service. Implements the authorization process with Facebook
     * throught OAuth.
     */
    private class LoginService implements Service {
        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {

            String code = context.getRequestParameter("code");
            String error = context.getRequestParameter("error");
            String userId = context.getAuthenticatedUserId();

            // If user is already logged in, redirect him to home page.
            if (userId != null) {
                context.getResponse().sendRedirect("/home/");
                return;
            }

            // If an error exsists in response, send it to the client
            if (error != null) {
                context.sendError(connectorException("Error from facebook: "
                    + error));
                return;
            }

            // If no error exsists and code is null, there is an error in the
            // response.
            // TODO handle this case better.
            if (code == null) {
                context.sendError(connectorException("Auth code is null."));
                return;
            }

            AccessToken token;
            try {
                token = FacebookConnector.performOauthValidation(code);
            } catch (FacebookConnectorException o) {
                context.sendError(connectorException(o));
                return;
            }

            UserResource userRes;

            try {
                userRes = UserResource.authenticateUser(token);
            } catch (ResourceException e) {
                context.sendError(e);
                return;
            }

            context.setAuthenticatedUserId(userRes.getUserId());
            context.getResponse().sendRedirect("/home/");
        }

        private ResourceException connectorException(String message) {
            return new ResourceException(
                ResourceErrorType.CONNECTOR_ERROR,
                message);
        }

        private ResourceException connectorException(Throwable t) {
            return new ResourceException(ResourceErrorType.CONNECTOR_ERROR, t);
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
