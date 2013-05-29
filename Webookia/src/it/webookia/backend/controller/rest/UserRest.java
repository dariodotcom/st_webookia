package it.webookia.backend.controller.rest;

import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.controller.rest.responses.JsonErrorResponse;
import it.webookia.backend.controller.rest.responses.JsonResponse;
import it.webookia.backend.controller.rest.responses.JsonSuccessResponse;
import it.webookia.backend.descriptor.Descriptor;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;

@Produces("application/json")
public class UserRest {

    private String username;

    public UserRest(String username) {
        this.username = username;
    }

    @GET
    public JsonResponse getUser() {
        try {
            Descriptor descriptor =
                UserResource.getUser(username).getDescriptor();
            return new JsonSuccessResponse(descriptor);
        } catch (ResourceException e) {
            return new JsonErrorResponse(e);
        }
    }
}
