package it.webookia.backend.controller.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServiceContext {

    private static final String loggedUserAttribute = "LOGGED_ATTR";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ServiceContext(HttpServletRequest request,
            HttpServletResponse response) {
        super();
        this.request = request;
        this.response = response;
    }

    public String getAuthenticatedUserId() {
        return (String) request.getSession().getAttribute(loggedUserAttribute);
    }

    public void setAuthenticatedUserId(String userID) {
        request.getSession().setAttribute(loggedUserAttribute, userID);
    }

    public Object getAttribute(String arg0) {
        return request.getAttribute(arg0);
    }

    public String getContextPath() {
        return request.getContextPath();
    }

    public String getParameter(String arg0) {
        return request.getParameter(arg0);
    }

    public void setAttribute(String arg0, Object arg1) {
        request.setAttribute(arg0, arg1);
    }

    // Standard getters
    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

}
