package it.webookia.backend.utils.foreignws.isbndb;

public class IsbnDBException extends Exception {

	private static final long serialVersionUID = -8902283222248135231L;

	public IsbnDBException(String message, Throwable cause) {
		super("IsbnDBException: " + message, cause);
	}

	public IsbnDBException(String message) {
		super("IsbnDBException: " + message);
	}

}
