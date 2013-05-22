package it.webookia.backend.controller.servlets;

import it.webookia.backend.controller.resources.UserResource;
import it.webookia.backend.utils.foreignws.facebook.AccessToken;
import it.webookia.backend.utils.foreignws.facebook.FacebookConnector;
import it.webookia.backend.utils.foreignws.facebook.OAuthException;

import java.io.IOException;

import javax.servlet.ServletException;

public class AuthenticationService extends ServiceServlet {
    
    private static final long serialVersionUID = 4703628228514306116L;

    protected AuthenticationService() {
        super("authentication");
        super.registerGetService("landing", new LoginLanding());
    }

    private class LoginLanding implements Service{

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            
            String code = context.getParameter("code");
            String error = context.getParameter("error");
            
            if(code != null){
                AccessToken token;
                try {
                    token = FacebookConnector.performOauthValidation(code);
                } catch(OAuthException o){
                    throw new ServletException(o);
                }
                
                UserResource userRes = UserResource.authenticateUser(token);
                context.setLoggedUserId(userRes.getId());
                // Redirect
                
            } else if(error != null){
                throw new ServletException("Error from oauth dialog: " + error);
            } else {
                throw new ServletException("Not a response from facebook");
            }
        }        
    }
    
    
}
