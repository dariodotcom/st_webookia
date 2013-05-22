package it.webookia.backend.controller.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServiceContext {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ServiceContext(HttpServletRequest request,
            HttpServletResponse response) {
        super();
        this.request = request;
        this.response = response;
    }

    // Standard getters
    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

}
