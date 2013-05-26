package it.webookia.backend.controller.resources.exception;

/**
 * Error that occurs during the elaboration of a request by a resource. It
 * carries a user friendly explanation plus a cause if the error was triggered
 * by another exception.
 */
public class ResourceException extends Exception {

    private static final long serialVersionUID = 7160398357284715016L;

    private ResourceErrorType errorType;

    /**
     * Constructs a {@link ResourceException} which contains only the error
     * type.
     * 
     * @param errorType
     *            - the {@link ResourceErrorType} that describes the cause of
     *            the error.
     */
    public ResourceException(ResourceErrorType errorType, String message) {
        super(errorType.getFriendlyText() + " - " + message);
        this.errorType = errorType;
    }

    /**
     * Constructs a {@link ResourceException} which contains the description of
     * the cause and the {@link Throwable} that triggered the error.
     * 
     * @param errorType
     *            - the {@link ResourceErrorType} that describes the cause of
     *            the error.
     * @param cause
     *            - the {@link Throwable} that triggered the error.
     */
    public ResourceException(ResourceErrorType errorType, Throwable cause) {
        super(errorType.getFriendlyText(), cause);
        this.errorType = errorType;
    }

    @Override
    public String toString() {
        String className = getClass().getName();
        String message = getLocalizedMessage();
        String result = className + " [" + errorType.getErrorName() + "]";
        return (message != null) ? (result + ": " + message) : result;
    }

    public ResourceErrorType getErrorType() {
        return errorType;
    }
}