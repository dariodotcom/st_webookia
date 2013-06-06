package it.webookia.backend.controller.services.impl;

import java.io.IOException;

import javax.servlet.ServletException;

public interface Service {
    public void service(ServiceContext context) throws ServletException,
            IOException;
}
