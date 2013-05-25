package it.webookia.backend.enums;

public enum PrivacyLevel {
    PUBLIC, FRIENDS_ONLY, PRIVATE;

    public static PrivacyLevel getDefault() {
        return PUBLIC;
    }
}
