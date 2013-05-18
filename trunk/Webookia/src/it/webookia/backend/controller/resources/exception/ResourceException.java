package it.webookia.backend.controller.resources.exception;

public class ResourceException extends Exception {

	private static final long serialVersionUID = 7160398357284715016L;

	private ResourceErrorType errorType;

	public ResourceException(ResourceErrorType errorType) {
		super(errorType.getFriendlyText());
		this.errorType = errorType;
	}

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