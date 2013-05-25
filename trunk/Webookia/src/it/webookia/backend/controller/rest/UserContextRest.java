package it.webookia.backend.controller.rest;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("user")
@Produces("application/json")
public class UserContextRest {

    @Path("{username}")
    public UserRest handleUser(@PathParam(value = "username") String username) {
        return new UserRest(username);
    }

}
