package it.webookia.backend.controller.rest;

import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.controller.rest.responses.ResponseFactory;
import it.webookia.backend.descriptor.Descriptor;
import it.webookia.backend.utils.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Produces("application/json")
public class UserRest {

    @Context
    private HttpServletRequest request;

    private String username;

    public UserRest(String username) {
        this.username = username;
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
        try {
            String requestorId = ServletUtils.getAuthenticatedUserId(request);
            UserResource requestor = UserResource.getUser(requestorId);

            Descriptor notificationDescriptor =
                UserResource.getUser(username).getNotifications(requestor);
            return ResponseFactory.createFrom(notificationDescriptor);
        } catch (ResourceException e) {
            return ResponseFactory.createFrom(e);
        }

    }
}
