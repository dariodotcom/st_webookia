package it.webookia.backend.controller.servlets;

import java.io.IOException;

import javax.servlet.ServletException;

public class UserService extends ServiceServlet {

    private static final long serialVersionUID = 351675044359094818L;

    protected UserService() {
        super("user");
        // TODO Auto-generated constructor stub
    }

    public static class UserProfileService implements Service {

        @Override
        public void service(ServiceContext context) throws ServletException,
                IOException {

        }

    }
}
