package it.webookia.backend.controller.servlets;

import it.webookia.backend.utils.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServiceContext {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ServiceContext(HttpServletRequest request,
            HttpServletResponse response) {
        super();
        this.request = request;
        this.response = response;
    }

    public String getAuthenticatedUserId() {
        return ServletUtils.getAuthenticatedUserId(request);
    }

    public void setAuthenticatedUserId(String userID) {
        ServletUtils.setAuthenticatedUserId(request, userID);
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
