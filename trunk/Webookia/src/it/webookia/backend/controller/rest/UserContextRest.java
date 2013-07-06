package it.webookia.backend.controller.rest;

import it.webookia.backend.controller.resources.NotificationResource;
import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceErrorType;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.controller.rest.responses.ResponseFactory;
import it.webookia.backend.descriptor.Descriptor;
import it.webookia.backend.utils.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("user")
@Produces("application/json")
public class UserContextRest {

    @Context
    private HttpServletRequest request;

    @Path("self")
    public UserRest handleSelf() {
        return new UserRest(null);
    }

    @Path("{username}")
    public UserRest handleUser(@PathParam(value = "username") String username) {
        return new UserRest(username);
    }

    @Produces("application/json")
    public class UserRest {

        private String username;

        public UserRest(String username) {
            if (username == null) {
                this.username = ServletUtils.getAuthenticatedUserId(request);
            } else {
                this.username = username;
            }
        }

        @GET
        public Response getUser() {
            try {

                Descriptor descriptor =
                    UserResource.getUser(username).getDescriptor();
                return ResponseFactory.createFrom(descriptor);
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }

        @Path("/notifications")
        @GET
        public Response getNotifications() {
            if (username == null) {
                return ResponseFactory.createFrom(new ResourceException(
                    ResourceErrorType.NOT_LOGGED_IN,
                    "No user logged in."));
            }

            try {
                String requestorId =
                    ServletUtils.getAuthenticatedUserId(request);
                UserResource requestor = UserResource.getUser(requestorId);

                Descriptor notificationDescriptor =
                    UserResource.getUser(username).getNotifications(requestor);
                return ResponseFactory.createFrom(notificationDescriptor);
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }

        @Path("/notifications/unreadCount")
        @GET
        public Response unreadCount() {
            if (username == null) {
                return ResponseFactory.createFrom(new ResourceException(
                    ResourceErrorType.NOT_LOGGED_IN,
                    "No user logged in."));
            }

            try {
                String requestorId =
                    ServletUtils.getAuthenticatedUserId(request);
                UserResource requestor = UserResource.getUser(requestorId);

                int count =
                    UserResource.getUser(username).getUnreadNotificationCount(
                        requestor);
                return ResponseFactory.ok(count);
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }

        @Path("/notification/{id}/read")
        @GET
        public Response markAsRead(@PathParam("id") String id) {
            if (username == null) {
                return ResponseFactory.createFrom(new ResourceException(
                    ResourceErrorType.NOT_LOGGED_IN,
                    "No user logged in."));
            }

            try {
                NotificationResource.getNotification(id).markAsRead();
                return ResponseFactory.ok();
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }

    }
}
