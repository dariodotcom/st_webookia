package it.webookia.backend.utils.foreignws.facebook;

/**
 * Exception that represents errors that may happen while connecting to
 * Facebook.
 * 
 * @author Chiara
 * 
 */
public class FacebookConnectorException extends Exception {

    private static final long serialVersionUID = -2115419977832811335L;

    public FacebookConnectorException() {
        super();
    }

    public FacebookConnectorException(String message) {
        super(message);
    }

}
