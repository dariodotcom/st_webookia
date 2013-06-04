package it.webookia.backend.controller.servlets;

import java.io.IOException;

import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.utils.ServletUtils;

import javax.servlet.ServletException;
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

    public Object getRequestAttribute(String arg0) {
        return request.getAttribute(arg0);
    }

    public String getContextPath() {
        return request.getContextPath();
    }

    public String getRequestParameter(String arg0) {
        return request.getParameter(arg0);
    }

    public void setRequestAttribute(String arg0, Object arg1) {
        request.setAttribute(arg0, arg1);
    }

    public void forwardToJsp(Jsp jsp) throws ServletException, IOException {
        request.getRequestDispatcher(jsp.getUrl()).forward(request, response);
    }

    public void sendError(ResourceException e) {
        // TODO: Implement error sending
    }

    // Standard getters
    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

}
