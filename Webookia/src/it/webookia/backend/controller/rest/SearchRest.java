package it.webookia.backend.controller.rest;

import it.webookia.backend.controller.resources.BookResource;
import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceErrorType;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.controller.rest.responses.ResponseFactory;
import it.webookia.backend.descriptor.BookDescriptor;
import it.webookia.backend.descriptor.ListDescriptor;
import it.webookia.backend.utils.ServletUtils;
import it.webookia.backend.utils.servlets.SearchParameters;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("search")
public class SearchRest {

    private HttpServletRequest request;

    public SearchRest(@Context HttpServletRequest request) {
        this.request = request;
    }

    @GET
    @Path("details")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchDetails() {
        SearchParameters parameters = SearchParameters.createFrom(request);
        return ResponseFactory.createFrom(BookResource
            .lookupDetailedBooks(parameters));
    }

    @GET
    @Path("/instances/{detailId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchConcretes(
            @PathParam(value = "detailId") String detailId) {

        ListDescriptor<BookDescriptor> result;

        String userId = ServletUtils.getAuthenticatedUserId(request);
        UserResource user;

        if (userId == null) {
            return ResponseFactory.createFrom(new ResourceException(
                ResourceErrorType.NOT_LOGGED_IN,
                "No user logged in."));
        }

        try {
            user = UserResource.getUser(userId);
            result = BookResource.lookupConcreteBooks(detailId, user);
        } catch (ResourceException e) {
            return ResponseFactory.createFrom(e);
        }

        return ResponseFactory.createFrom(result);
    }
}