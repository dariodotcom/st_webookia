package it.webookia.backend.controller.rest.responses;

import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.descriptor.Descriptor;

import javax.ws.rs.core.Response;

public class ResponseFactory {

    public static Response createFrom(ResourceException exception) {

        Response.Status status;

        switch (exception.getErrorType()) {
        case NOT_FOUND:
            status = Response.Status.NOT_FOUND;
            break;
        case UNAUTHORIZED_ACTION:
            status = Response.Status.FORBIDDEN;
            break;
        case SERVER_FAULT:
            status = Response.Status.INTERNAL_SERVER_ERROR;
            break;
        default:
            status = Response.Status.BAD_REQUEST;
        }

        // Print exception on stack trace when debugging
        System.err
            .println("Rest interface returned exception message to client:");
        exception.printStackTrace(System.err);

        JsonResponse resp = new JsonErrorResponse(exception);
        return Response.status(status).entity(resp).build();
    }

    public static Response createFrom(Descriptor descriptor) {
        JsonResponse resp = new JsonSuccessResponse(descriptor);
        return Response.ok().entity(resp).build();
    }
}
