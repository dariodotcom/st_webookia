package it.webookia.backend.controller.services;

import it.webookia.backend.controller.services.impl.Jsp;
import it.webookia.backend.controller.services.impl.Service;
import it.webookia.backend.controller.services.impl.ServiceContext;
import it.webookia.backend.controller.services.impl.ServiceServlet;
import it.webookia.backend.controller.services.impl.Verb;

import java.io.IOException;

import javax.servlet.ServletException;

public class Home extends ServiceServlet {

    private static final long serialVersionUID = 6199421201754122052L;

    public Home() {
        super("home");
        registerDefaultService(Verb.GET, new HomeService());
    }

    private class HomeService implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {
            context.forwardToJsp(Jsp.HOME);
        }

    }

}
