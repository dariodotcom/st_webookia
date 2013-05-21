package it.webookia.backend.controller.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServiceServlet extends HttpServlet{

    private Map<String, Service> getServices;
    private Map<String, Service> postServices;
    private String sectionName;
    
    protected ServiceServlet(String section){
        this.getServices = new HashMap<String, Service>();
        this.postServices = new HashMap<String, Service>();
        this.sectionName = section;
    }
    
    protected void registerGetService(String action, Service service){
        this.getServices.put(action, service);
    }
    
    protected void registerPostService(String action, Service service){
        this.postServices.put(action, service);
    }

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        
        
    }

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doPost(req, resp);
    }
    
    private String getAction(HttpServletRequest request) throws ServletException{
        String requestAction = request.getRequestURI().replace(request.getContextPath(), "");
        String[] components = requestAction.split("/");
        
        if(!components[0].equals(this.sectionName)){
            
        }
        
    }
    
    
}
