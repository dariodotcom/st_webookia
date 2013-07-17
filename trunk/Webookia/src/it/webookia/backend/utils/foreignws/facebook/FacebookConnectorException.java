package it.webookia.backend.utils.foreignws.facebook;

/**
 * Exception that represents errors that may happen while connecting to
 * Facebook.
 */
public class FacebookConnectorException extends Exception {

    private static final long serialVersionUID = -2115419977832811335L;

    /**
     * Class constructor
     */
    public FacebookConnectorException() {
        super();
    }

    /**
     * Class constructor
     * 
     * @param message
     *            a specific message of error
     */
    public FacebookConnectorException(String message) {
        super(message);
    }

}
