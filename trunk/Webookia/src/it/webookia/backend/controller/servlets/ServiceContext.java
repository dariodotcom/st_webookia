package it.webookia.backend.controller.servlets;

import java.io.IOException;

import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.NewCookie;

/**
 * The context in which the service is executed. It wraps http request and
 * response, providing ways to perform common tasks on them.
 */
public class ServiceContext {

    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs a {@link NewCookie} {@code ServiceContext}
     * 
     * @param request
     *            - the {@link HttpServletRequest} to manage.
     * @param response
     *            - the {@link HttpServletResponse} to manage.
     */
    public ServiceContext(HttpServletRequest request,
            HttpServletResponse response) {
        super();
        this.request = request;
        this.response = response;
    }

    /**
     * Gets the ID of the authenticated user from the session.
     * 
     * @return - the user ID retrieved from the session
     */
    public String getAuthenticatedUserId() {
        return ServletUtils.getAuthenticatedUserId(request);
    }

    /**
     * Sets the authenticated user id. It will be stored in the session so that
     * in case of future requests from the same customer we can retrieve this
     * value.
     * 
     * @param userID
     *            - the ID of the authenticated user.
     */
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

    public void sendRedirect(String newUrl) throws IOException {
        response.sendRedirect(newUrl);
    }

    // Standard getters
    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

}
