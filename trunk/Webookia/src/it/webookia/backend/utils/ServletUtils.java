package it.webookia.backend.utils;

import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.descriptor.UserDescriptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

    private static final String AUTH_USER = "AUTH_USER";
    private static final String AUTH_USER_DESCRIPTOR = "AUTH_USER_DESCRIPTOR";
    private static final String CONCRETE_BOOK = "CONCRETE_BOOK";

    /* Authenticate userd id getters and setters */
    public static String getAuthenticatedUserId(HttpServletRequest req) {
        return (String) req.getSession().getAttribute(AUTH_USER);
    }

    public static void setAuthenticatedUserId(HttpServletRequest req, String id) {
        req.getSession().setAttribute(AUTH_USER, id);
    }

    public static UserResource getAuthenticatedUser(HttpServletRequest req)
            throws ServletException {
        try {
            return UserResource.getUser(getAuthenticatedUserId(req));
        } catch (ResourceException e) {
            throw new ServletException(e);
        }
    }

    public static String getConcreteBookId(HttpServletRequest req) {
        return (String) req.getSession().getAttribute("CONCRETE_BOOK");
    }

    public static void setConcreteBookId(HttpServletRequest req, String id) {
        req.getSession().setAttribute(CONCRETE_BOOK, id);
    }

    public static UserDescriptor getLoggedUserDescriptor(
            HttpServletRequest request) {
        Object obj = request.getAttribute(AUTH_USER_DESCRIPTOR);
        if (obj == null) {
            return null;
        } else {
            return (UserDescriptor) obj;
        }
    }

    public static void setAuthenticatedUserDescriptor(
            HttpServletRequest request, UserDescriptor descriptor) {
        request.setAttribute(AUTH_USER_DESCRIPTOR, descriptor);
    }

    public static <T> T getRequestAttribute(HttpServletRequest request,
            Class<T> attributeType, String attributeName)
            throws ServletException {
        Object obj = request.getAttribute(attributeName);
        try {
            return attributeType.cast(obj);
        } catch (ClassCastException e) {
            throw new ServletException(e);
        }
    }
}
