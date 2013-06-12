package it.webookia.backend.controller.services;

import it.webookia.backend.controller.services.impl.Service;
import it.webookia.backend.controller.services.impl.ServiceContext;
import it.webookia.backend.controller.services.impl.ServiceServlet;
import it.webookia.backend.utils.servlets.Context;

import java.io.IOException;

import javax.servlet.ServletException;

public class Users extends ServiceServlet {

    private static final long serialVersionUID = 351675044359094818L;

    public Users() {
        super(Context.USERS);
        // TODO Auto-generated constructor stub
    }

    public static class UserProfileService implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {

        }

    }
}
