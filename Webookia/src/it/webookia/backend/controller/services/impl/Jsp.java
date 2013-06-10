package it.webookia.backend.controller.services.impl;

public enum Jsp {

    HOME("/home.jsp"),
    BOOK_JSP("/books.jsp"),
    BOOK_DETAIL_JSP("/book_detail.jsp"),
    LOAN_DETAIL("/loan_detail.jsp"),
    LOAN_JSP("/loans.jsp");

    private String url;

    private Jsp(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
