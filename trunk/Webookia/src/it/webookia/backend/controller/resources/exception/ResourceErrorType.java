package it.webookia.backend.controller.resources.exception;

public enum ResourceErrorType {

	NOT_FOUND("Resource not found"),
	SERVER_FAULT("Internal server error"),
	UNAUTHORIZED_ACTION("Authorization error"),
	ALREADY_EXSISTING("Entity already exsists"),
	RESOURCE_UNAVAILABLE("Resource unavailable"),
	ILLEGAL_STATE("Illegal state"),
	BAD_REQUEST("Bad request");

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
