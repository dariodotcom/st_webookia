package it.webookia.backend.utils;

import it.webookia.backend.descriptor.UserDescriptor;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

    private static final String AUTH_USER = "AUTH_USER";
    private static final String AUTH_USER_DESCRIPTOR = "AUTH_USER_DESCRIPTOR";

    public static String getAuthenticatedUserId(HttpServletRequest req) {
        return (String) req.getSession().getAttribute(AUTH_USER);
    }

    public static void setAuthenticatedUserId(HttpServletRequest req, String id) {
        req.getSession().setAttribute(AUTH_USER, id);
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

    public static void setAuthenticatedUserDescriptor(HttpServletRequest request,
            UserDescriptor descriptor) {
        request.setAttribute(AUTH_USER_DESCRIPTOR, descriptor);
    }
}
