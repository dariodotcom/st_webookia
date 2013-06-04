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

    /**
     * Retrieves an attribute from the request.
     * 
     * @param - the key associated with the attribute.
     * @return - the attribute retrieved from the request.
     */
    public Object getRequestAttribute(String arg0) {
        return request.getAttribute(arg0);
    }

    /**
     * Adds an attribute in the request.
     * 
     * @param - the key which will be linked to the attribute
     * @param - the attribute to add.
     */
    public void setRequestAttribute(String arg0, Object arg1) {
        request.setAttribute(arg0, arg1);
    }

    /**
     * Returns the request context path.
     * 
     * @return the request context path.
     */
    public String getContextPath() {
        return request.getContextPath();
    }

    /**
     * Retrieves a parameter from the request.
     * 
     * @param - the parameter key.
     * @return - the parameter.
     */
    public String getRequestParameter(String arg0) {
        return request.getParameter(arg0);
    }

    /**
     * Forward this request to a jsp.
     * 
     * @param - the JSP to forward the page to.
     * @throws ServletException
     *             - if an error occurs.
     * @throws IOException
     *             - if an error occurs.
     */
    public void forwardToJsp(Jsp jsp) throws ServletException, IOException {
        request.getRequestDispatcher(jsp.getUrl()).forward(request, response);
    }

    /**
     * Send an error response to the user.
     * 
     * @param e
     *            - the {@link ResourceException} that caused the error.
     */
    public void sendError(ResourceException e) {
        // TODO: Implement error sending
    }

    /**
     * Redirects a request to a new url.
     * 
     * @param newUrl
     *            - the url to which to redirect the request.
     * @throws IOException
     *             - if an error occurs.
     */
    public void sendRedirect(String newUrl) throws IOException {
        response.sendRedirect(newUrl);
    }

    /**
     * Retrieves the contained {@link HttpServletRequest}
     * 
     * @return - the {@link HttpServletRequest}
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Retrieves the contained {@link HttpServletResponse}
     * 
     * @return - the {@link HttpServletResponse}
     */
    public HttpServletResponse getResponse() {
        return response;
    }

}
