package it.webookia.backend.utils.foreignws.isbndb;

/**
 * The Class {@code IsbnDBException} represents an error occurred while
 * performing a ISBN resolver request.
 */
public class IsbnResolverException extends Exception {

    private static final long serialVersionUID = -8902283222248135231L;

    /**
     * Constructs a new instance given a {@code String} message and the
     * {@code Throwable} that caused the exception.
     * 
     * @param message
     *            - a {@code String} explanation of the error that occurred.
     * @param cause
     *            - the {@code Throwable} that caused this exception.
     */
    public IsbnResolverException(String message, Throwable cause) {
        super("IsbnDBException: " + message, cause);
    }

    /**
     * Constructs a new instance given a {@code String} message.
     * 
     * @param message
     *            - a {@code String} explanation of the error that occurred.
     */
    public IsbnResolverException(String message) {
        super("IsbnDBException: " + message);
    }

}
