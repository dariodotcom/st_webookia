package it.webookia.backend.enums;

public enum LoanStatus {
    INITIAL, ACCEPTED, SHIPPED, GIVEN_BACK, REFUSED;

    public static LoanStatus valueOf(int i) {
        if (i >= values().length) {
            throw new IndexOutOfBoundsException();
        }

        return values()[i];
    }

    public static int indexOf(LoanStatus status) {
        LoanStatus[] values = values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(status)) {
                return i;
            }
        }

        return -1;
    }
}
