package it.webookia.backend.utils.servlets;

public enum Context {
    HOME("home", false, true),
    BOOKS("books", true, true),
    LOANS("loans", true, true),
    USERS("users", true, true),
    SEARCH("search", true, true),
    AUTHENTICATION("authentication", false, false);

    private String contextName;
    private boolean showInMenu;
    private boolean requiresLogin;

    private Context(String contextName, boolean requiresLogin, boolean showInMenu) {
        this.contextName = contextName;
        this.requiresLogin = requiresLogin;
        this.showInMenu = showInMenu;
    }

    public String getContextName() {
        return contextName;
    }
    
    public boolean requiresLogin(){
        return requiresLogin;
    }
    
    public boolean showInMenu(){
        return showInMenu;
    }

}
