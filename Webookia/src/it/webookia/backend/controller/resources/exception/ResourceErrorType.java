package it.webookia.backend.controller.resources.exception;

public enum ResourceErrorType {

	NOT_FOUND("Resource not found."),
	SERVER_FAULT("Internal server error."),
	UNAUTHORIZED_ACTION("You don't have the right to perform such action"),
	ALREADY_EXSISTING("Entity already exsists."),
	RESOURCE_UNAVAILABLE(""),
	ILLEGAL_STATE(""),
	BAD_REQUEST("");

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
