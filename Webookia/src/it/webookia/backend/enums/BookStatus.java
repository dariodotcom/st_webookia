package it.webookia.backend.enums;

public enum BookStatus {
    LENT, AVAILABLE;

    public static BookStatus getDefault() {
        return AVAILABLE;
    }
}
