package it.webookia.backend.controller.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Service {
    public void apply(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
}
