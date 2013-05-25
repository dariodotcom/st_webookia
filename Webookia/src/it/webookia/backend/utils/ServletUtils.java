package it.webookia.backend.utils;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

    private static final String AUTH_USER = "AUTH_USER";

    public static String getAuthenticatedUsername(HttpServletRequest req) {
        // return (String) req.getSession().getAttribute(AUTH_USER);
        return "dario.archetti";
    }

    public static void setAuthenticatedUsername(HttpServletRequest req,
            String id) {
        req.getSession().setAttribute(AUTH_USER, id);
    }
}
