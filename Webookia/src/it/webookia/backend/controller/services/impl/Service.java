package it.webookia.backend.controller.services.impl;

import java.io.IOException;

import javax.servlet.ServletException;
/**
 * Represents an interface implemented by each method that realizes the logic of the model and makes it available to a user.
 *
 */
public interface Service {
    public void service(ServiceContext context) throws ServletException,
            IOException;
}
