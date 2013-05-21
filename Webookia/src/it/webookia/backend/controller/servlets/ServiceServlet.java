package it.webookia.backend.controller.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServiceServlet extends HttpServlet {

    private static final long serialVersionUID = -6364544534456443497L;

    private Map<String, Service> getServices;
    private Map<String, Service> postServices;
    private String contextName;

    protected ServiceServlet(String section) {
        this.getServices = new HashMap<String, Service>();
        this.postServices = new HashMap<String, Service>();
        this.contextName = section;
    }

    protected final void registerGetService(String action, Service service) {
        this.getServices.put(action, service);
    }

    protected final void registerPostService(String action, Service service) {
        this.postServices.put(action, service);
    }

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String actionName = getAction(req);
        if (!getServices.keySet().contains(actionName)) {
            throw new ServletException(String.format(
                "No GET handler for action %s in context %s",
                actionName,
                contextName));
        }

        getServices.get(actionName).service(req, resp);
    }

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String actionName = getAction(req);
        if (!postServices.keySet().contains(actionName)) {
            throw new ServletException(String.format(
                "No POST handler for action %s in context %s",
                actionName,
                contextName));
        }

        postServices.get(actionName).service(req, resp);
    }

    private String getAction(HttpServletRequest request)
            throws ServletException {
        String requestAction =
            request.getRequestURI().replace(request.getContextPath(), "");
        String[] components = requestAction.split("/");

        if (!components[0].equals(this.contextName)) {
            String message = "Service context error. Managed: %s, received: %s";
            throw new ServletException(String.format(
                message,
                components[0],
                this.contextName));
        }

        return components[1];
    }

}
