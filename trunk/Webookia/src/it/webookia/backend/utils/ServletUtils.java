package it.webookia.backend.utils;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

    private static final String AUTH_USER = "AUTH_USER";

    public static String getAuthenticatedUserId(HttpServletRequest req) {
        return (String) req.getSession().getAttribute(AUTH_USER);
    }

    public static void setAuthenticatedUserId(HttpServletRequest req,
            String id) {
        req.getSession().setAttribute(AUTH_USER, id);
    }
}
