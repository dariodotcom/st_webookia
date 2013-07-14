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
        return new UserRest();
    }

    @Path("{userid}")
    public UserRest handleUser(@PathParam(value = "userid") String userid) {
        return new UserRest(userid);
    }

    @Produces("application/json")
    public class UserRest {

        private String userid;

        public UserRest() {
            this.userid = ServletUtils.getAuthenticatedUserId(request);
        }

        public UserRest(String userid) {
            this.userid = userid;
        }

        @GET
        public Response getUser() {
            if (userid == null) {
                return ResponseFactory.createFrom(new ResourceException(
                    ResourceErrorType.NOT_LOGGED_IN,
                    "No user logged in."));
            }

            try {
                UserResource user = UserResource.getUser(userid);
                return ResponseFactory.createFrom(user.getDescriptor());
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }

        @Path("/books")
        @GET
        public Response getBooks() {
            if (userid == null) {
                return ResponseFactory.createFrom(new ResourceException(
                    ResourceErrorType.NOT_LOGGED_IN,
                    "No user logged in."));
            }

            String requestorId = ServletUtils.getAuthenticatedUserId(request);

            try {
                UserResource requestor = UserResource.getUser(requestorId);
                UserResource target = UserResource.getUser(userid);
                return ResponseFactory.createFrom(target
                    .getVisibleBooks(requestor));
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }

        @Path("/feedbacks/borrower")
        public Response getFeedbacksAsBorrower() {
            if (userid == null) {
                return ResponseFactory.createFrom(new ResourceException(
                    ResourceErrorType.NOT_LOGGED_IN,
                    "No user logged in."));
            }

            try {
                UserResource user = UserResource.getUser(userid);
                return ResponseFactory.createFrom(user.getFeedbacksAsBorrower());
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }

        @Path("/feedbacks/owner")
        public Response getFeedbacksAsOwner() {
            if (userid == null) {
                return ResponseFactory.createFrom(new ResourceException(
                    ResourceErrorType.NOT_LOGGED_IN,
                    "No user logged in."));
            }

            try {
                UserResource user = UserResource.getUser(userid);
                return ResponseFactory.createFrom(user.getFeedbacksAsOwner());
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }

        @Path("/notifications")
        @GET
        public Response getNotifications() {
            if (userid == null) {
                return ResponseFactory.createFrom(new ResourceException(
                    ResourceErrorType.NOT_LOGGED_IN,
                    "No user logged in."));
            }

            try {
                String requestorId =
                    ServletUtils.getAuthenticatedUserId(request);
                UserResource requestor = UserResource.getUser(requestorId);

                Descriptor notificationDescriptor =
                    UserResource.getUser(userid).getNotifications(requestor);
                return ResponseFactory.createFrom(notificationDescriptor);
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }

        @Path("/notifications/unreadCount")
        @GET
        public Response unreadCount() {
            try {
                String requestorId =
                    ServletUtils.getAuthenticatedUserId(request);
                UserResource requestor = UserResource.getUser(requestorId);
                int count =
                    UserResource.getUser(userid).getUnreadNotificationCount(
                        requestor);
                return ResponseFactory.ok(count);
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }

        @Path("/notification/{id}/read")
        @GET
        public Response markAsRead(@PathParam("id") String id) {
            try {
                String requestorId =
                    ServletUtils.getAuthenticatedUserId(request);
                UserResource requestor = UserResource.getUser(requestorId);
                NotificationResource.getNotification(id).markAsRead(requestor);
                return ResponseFactory.ok();
            } catch (ResourceException e) {
                return ResponseFactory.createFrom(e);
            }
        }

    }
}
