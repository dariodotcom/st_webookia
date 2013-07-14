package it.webookia.backend.controller.resources.exception;

/**
 * This class provides a list of the possible error types that could be received
 * by a user when a failure happens.
 * 
 */
public enum ResourceErrorType {

    NOT_FOUND("Resource not found"),
    SERVER_FAULT("Internal server error"),
    UNAUTHORIZED_ACTION("Authorization error"),
    NOT_LOGGED_IN("Not logged in"),
    ALREADY_EXSISTING("Entity already exsists"),
    RESOURCE_UNAVAILABLE("Resource unavailable"),
    ILLEGAL_STATE("Illegal state"),
    BAD_REQUEST("Bad request"),
    CONNECTOR_ERROR("Error connecting to Facebook."),
    ISBN_RESOLVER_ERROR("Error while resolving book by ISBN"),
    ALREADY_OWNED("You already own this book");

    private String errorName;
    private String friendlyText;

    private ResourceErrorType(String friendlyText) {
        this.friendlyText = friendlyText;
        this.errorName = this.name();
    }

    public String getErrorName() {
        return errorName;
    }

    public String getFriendlyText() {
        return friendlyText;
    }

}
