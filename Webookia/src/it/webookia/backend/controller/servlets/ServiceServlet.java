package it.webookia.backend.controller.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServiceServlet extends HttpServlet {

    private static final long serialVersionUID = -6364544534456443497L;
    private static final Pattern requestPattern = Pattern
        .compile("/([a-z|A-Z]+)/([a-z|A-Z]*)[.]*");

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

        ServiceContext context = new ServiceContext(req, resp);
        getServices.get(actionName).service(context);
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

        ServiceContext context = new ServiceContext(req, resp);
        postServices.get(actionName).service(context);
    }

    private String getAction(HttpServletRequest request)
            throws ServletException {
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String relativePath = requestURI.replace(contextPath, "");

        Matcher m = requestPattern.matcher(relativePath);

        if (!m.matches()) {
            throw new ServletException("Bad Request: " + relativePath);
        }

        String context = m.group(1), action = m.group(2);

        if (!context.equals(this.contextName)) {
            throw new ServletException("Service context error: " + context);
        }

        return action;
    }
}
