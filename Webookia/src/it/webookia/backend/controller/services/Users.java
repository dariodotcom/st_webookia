package it.webookia.backend.controller.services;

import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceErrorType;
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
 * This is the servlet which manages users in Webookia, allowing a user to display his personal page and other users' personal pages.
 *
 */
public class Users extends ServiceServlet {

    private static final long serialVersionUID = 351675044359094818L;
    /**
     * Indicates a context where the user is logged in and can display personal information.
     */
    public static String SHOW_USER = "SHOW_USER";
   
    /**
     * Class constructor
     */
    public Users() {
        super(Context.USERS);
        registerDefaultService(Verb.GET, new UserLanding());
        registerService(Verb.GET, "profile", new UserProfileService());
    }
    
    /**
     * This service allows a user to jump on its personal homepage after his first authentication.
     *
     */
    public static class UserLanding implements Service {
        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            if (!context.isUserLoggedIn()) {
                context.sendError(new ResourceException(
                    ResourceErrorType.NOT_LOGGED_IN,
                    "You need to login to access this section."));
                return;
            }

            String userId = context.getAuthenticatedUserId();

            try {
                UserResource user = UserResource.getUser(userId);
                context.setRequestAttribute(SHOW_USER, user);
                context.forwardToJsp(Jsp.USERS_JSP);
            } catch (ResourceException e) {
                context.sendError(e);
            }
        }
    }
    /**
     * This service allows a user to visualize his personal homepage.
     *
     */
    public static class SelfProfileService implements Service {
        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            if (!context.isUserLoggedIn()) {
                context.sendError(new ResourceException(
                    ResourceErrorType.BAD_REQUEST,
                    "No profile to show."));
                return;
            }

            String userId = context.getAuthenticatedUserId();

            try {
                UserResource user = UserResource.getUser(userId);
                context.setRequestAttribute(SHOW_USER, user);
                context.forwardToJsp(Jsp.USER_JSP);
            } catch (ResourceException e) {
                context.sendError(e);
            }
        }
    }
    /**
     * This service allows a user to visualize another user personal homepage.
     *
     */
    public static class UserProfileService implements Service {
        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            String userId = context.getRequestParameter("uid");

            if (userId == null) {
                new SelfProfileService().service(context);
                return;
            }

            try {
                UserResource user = UserResource.getUser(userId);
                context.setRequestAttribute(SHOW_USER, user);
                context.forwardToJsp(Jsp.USER_JSP);
            } catch (ResourceException e) {
                context.sendError(e);
            }
        }
    }
}
