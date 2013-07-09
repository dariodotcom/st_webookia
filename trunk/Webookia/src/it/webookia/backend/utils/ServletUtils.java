package it.webookia.backend.utils;

import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.descriptor.UserDescriptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Container class for helper methods of servlets.
 */
public class ServletUtils {

    private static final String AUTH_USER = "AUTH_USER";
    private static final String AUTH_USER_DESCRIPTOR = "AUTH_USER_DESCRIPTOR";

    /**
     * Retrieves the authenticated user id from the session.
     * 
     * @param req
     *            - the active request that contains the session to retrieve the
     *            id from.
     * @return the id of the authenticated user, null if no user is
     *         authenticated.
     */
    public static String getAuthenticatedUserId(HttpServletRequest req) {
        return (String) req.getSession().getAttribute(AUTH_USER);
    }

    /**
     * Sets the authenticated user id
     * 
     * @param req
     *            - the requestthat contains the session in which to set the
     *            user id.
     * @param id
     *            - the id of the authenticated user.
     */
    public static void setAuthenticatedUserId(HttpServletRequest req, String id) {
        req.getSession().setAttribute(AUTH_USER, id);
    }

    /**
     * Retrieves the authenticated user from a session using
     * {@link UserResource} methods.
     * 
     * @param req
     *            - the request that contains the session to retrieve the
     *            authenticated user from.
     * @return - the {@link UserResource} holding the authenticated user.
     * @throws ResourceException
     *             if thrown by {@link UserResource} methods.
     */
    public static UserResource getAuthenticatedUser(HttpServletRequest req)
            throws ResourceException {
        return UserResource.getUser(getAuthenticatedUserId(req));

    }

    /**
     * Retrieves authenticated user descriptor from a request.
     * 
     * @param request
     *            - the request
     * @return the descriptor, null if not found.
     */
    public static UserDescriptor getLoggedUserDescriptor(
            HttpServletRequest request) {
        Object obj = request.getAttribute(AUTH_USER_DESCRIPTOR);
        if (obj == null) {
            return null;
        } else {
            return (UserDescriptor) obj;
        }
    }

    /**
     * Sets the user descriptor in a request.
     * 
     * @param request
     *            - the request
     * @param descriptor
     *            - the descriptor to set.
     */
    public static void setAuthenticatedUserDescriptor(
            HttpServletRequest request, UserDescriptor descriptor) {
        request.setAttribute(AUTH_USER_DESCRIPTOR, descriptor);
    }

    /**
     * Retrieves an attribute from the request
     * 
     * @param request
     *            - the {@link HttpServletRequest} to retrieve the attribute
     *            from.
     * @param attributeType
     *            - the {@link Class} of the attribute.
     * @param attributeName
     *            - the name of the attribute
     * @return - the attribute value or null if no attribute matches with given
     *         name.
     * @throws ServletException
     *             if a {@link ClassCastException} is raised while casting the
     *             retrieved attribute.
     */
    public static <T> T getRequestAttribute(HttpServletRequest request,
            Class<T> attributeType, String attributeName)
            throws ServletException {
        Object obj = request.getAttribute(attributeName);

        if (obj == null) {
            return null;
        }

        try {
            return attributeType.cast(obj);
        } catch (ClassCastException e) {
            throw new ServletException(e);
        }
    }
}
