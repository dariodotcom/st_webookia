package it.webookia.backend.utils.servlets;

public enum Context {
    HOME("home"), BOOKS("books"), LOANS("loans"), USERS("users"), SEARCH(
            "search"), AUTHENTICATION("authentication");

    private String contextName;

    private Context(String contextName) {
        this.contextName = contextName;
    }

    public String getContextName() {
        return contextName;
    }

}
