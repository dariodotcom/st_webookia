package it.webookia.backend.controller.services.impl;

import it.webookia.backend.utils.servlets.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.datanucleus.util.StringUtils;

public class ServiceServlet extends HttpServlet {

    public static final String CONTEXT = "CONTEXT";

    private static final long serialVersionUID = -6364544534456443497L;
    private static final Pattern requestPattern = Pattern
        .compile("/(([a-z|A-Z]+)/([a-z|A-Z]*)[.]*)?");

    private Context context;

    private Map<String, Service> getServices;
    private Map<String, Service> postServices;
    private Service defaultGetService;
    private Service defaultPostService;

    protected ServiceServlet(Context context) {
        this.getServices = new HashMap<String, Service>();
        this.postServices = new HashMap<String, Service>();
        this.context = context;
    }

    protected final void registerDefaultService(Verb verb, Service service) {
        if (verb.equals(Verb.GET)) {
            this.defaultGetService = service;
        } else {
            this.defaultPostService = service;
        }
    }

    protected final void registerService(Verb verb, String action,
            Service service) {
        Map<String, Service> target =
            verb.equals(Verb.GET) ? getServices : postServices;

        target.put(action, service);
    }

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        runService(Verb.GET, req, resp);
    }

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        runService(Verb.POST, req, resp);
    }

    private void runService(Verb verb, HttpServletRequest req,
            HttpServletResponse resp) throws ServletException, IOException {
        String actionName;
        ServiceContext serviceContext = new ServiceContext(req, resp);

        try {
            actionName = getActionName(req);
        } catch (IllegalArgumentException e) {
            throw new ServletException(e);
        }

        req.setAttribute(CONTEXT, context);

        if (StringUtils.isEmpty(actionName)) {
            Service def =
                verb.equals(Verb.GET) ? defaultGetService : defaultPostService;

            if (def == null) {
                throw new ServletException("No default service for "
                    + context.getContextName());
            }

            def.service(serviceContext);
            return;
        }

        Map<String, Service> servicePool =
            verb.equals(Verb.GET) ? getServices : postServices;

        if (!servicePool.keySet().contains(actionName)) {
            throw new ServletException("No service found for action "
                + actionName);
        }

        servicePool.get(actionName).service(serviceContext);
    }

    private String getActionName(HttpServletRequest request)
            throws IllegalArgumentException {
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String relativePath = requestURI.replace(contextPath, "");

        Matcher m = requestPattern.matcher(relativePath);

        if (!m.matches()) {
            throw new IllegalArgumentException("Bad request: " + relativePath);
        }

        String context = (m.group(2) == null ? "" : m.group(2));
        String action = (m.group(3) == null ? "" : m.group(3));

        if (!context.equals(this.context.getContextName())) {
            throw new IllegalArgumentException("Service context error: "
                + context);
        }

        return action;
    }
}
