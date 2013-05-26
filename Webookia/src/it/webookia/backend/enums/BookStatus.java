package it.webookia.backend.enums;

public enum BookStatus {
    LENT, AVAILABLE, NOT_AVAILABLE;

    public static BookStatus getDefault() {
        return AVAILABLE;
    }
}
