package it.webookia.backend.controller.updater;

import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.controller.updater.UpdateNotification.UpdateEntry;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("update")
public class FacebookUpdateHandler {

    private static final String MODE = "subscribe";
    private static final String MODE_PARAM = "hub.mode";
    private static final String CHALLENGE_PARAM = "hub.challenge";
    private static final String VERIFY_TOKEN = "hub.verify_token";

    private final static String SECRET_VERIFY_STRING = "ewr4t56d5f6g7yu";
    @Context
    private HttpServletRequest request;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response get() {
        String mode = request.getParameter(MODE_PARAM);
        String challenge = request.getParameter(CHALLENGE_PARAM);
        String token = request.getParameter(VERIFY_TOKEN);
        final Response badRequest =
            Response.status(Response.Status.BAD_REQUEST).build();

        if (mode == null || challenge == null || token == null) {
            return badRequest;
        }

        // Ensure mode matches with that issued by facebook.
        if (!mode.equals(MODE)) {
            return badRequest;
        }

        // Ensure verify token matches with ours.
        if (!token.equals(SECRET_VERIFY_STRING)) {
            return badRequest;
        }

        // Echo challenge param
        return Response.ok(challenge).build();

    }

    public Response post(UpdateNotification notification) {
        if (!notification.getObject().equals("user")) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        for (UpdateEntry entry : notification.getEntry()) {
            String uid = entry.getUid();
            try {
                UserResource res = UserResource.getUser(uid);
                res.updateFields(entry.getChanged_fields());
            } catch (ResourceException e) {
                System.err.println("Skipping update of "
                    + entry.getUid()
                    + " because of:");
                e.printStackTrace(System.err);
                continue;
            }
        }

        return Response.ok().build();
    }

}
