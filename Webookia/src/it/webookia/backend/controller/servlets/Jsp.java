package it.webookia.backend.controller.servlets;

public enum Jsp {

    HOME("/home.jsp"),
    BOOK_JSP("/book.jsp");

    private String url;

    private Jsp(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
