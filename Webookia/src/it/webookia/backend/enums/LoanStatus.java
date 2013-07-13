package it.webookia.backend.enums;

import it.webookia.backend.model.Loan;

/**
 * Enumeration of possible {@link Loan} status.
 */
public enum LoanStatus {
    INITIAL, ACCEPTED, SHIPPED, GIVEN_BACK, REFUSED;

    /**
     * Gives the {@link LoanStatus} with given index
     * 
     * @param i
     *            - the index
     * @return - the {@link LoanStatus} at index i.
     */
    public static LoanStatus valueOf(int i) {
        if (i >= values().length) {
            throw new IndexOutOfBoundsException();
        }

        return values()[i];
    }

    /**
     * Retrieves the index of a given status in the array of {@link LoanStatus}
     * values.
     * 
     * @param status
     *            - the status
     * @return the index
     */
    public static int indexOf(LoanStatus status) {
        LoanStatus[] values = values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(status)) {
                return i;
            }
        }

        return -1; // This should never happen
    }
}
