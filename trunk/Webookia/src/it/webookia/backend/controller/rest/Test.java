package it.webookia.backend.controller.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

@Path("home")
public class Test {

    @Context
    HttpServletRequest request;

    @GET
    @Produces("text/html")
    public String home() {
        System.out.println(request.getParameter("lol"));
        return "<b>Home</b>";
    }

}
