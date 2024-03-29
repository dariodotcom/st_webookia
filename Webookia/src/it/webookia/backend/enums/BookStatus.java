package it.webookia.backend.enums;

/**
 * This class provides a list of all the possible status of a concrete book.
 */
public enum BookStatus {
    LENT, AVAILABLE, NOT_AVAILABLE;

    /**
     * Class constructor.
     * 
     * @return the default book status value
     */
    public static BookStatus getDefault() {
        return AVAILABLE;
    }
}
