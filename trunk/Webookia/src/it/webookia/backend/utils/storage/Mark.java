package it.webookia.backend.utils.storage;

import java.io.Serializable;

public class Mark implements Serializable {

    private static final long serialVersionUID = 3786089801130448034L;
    private static final String exceptionMessage =
        "Mark has to be an integer between %s and %s, given: %s";

    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 5;

    public static Mark create(int mark) {
        if (mark < MIN_VALUE || mark > MAX_VALUE) {
            String message =
                String.format(exceptionMessage, MIN_VALUE, MAX_VALUE, mark);
            throw new IllegalArgumentException(message);
        }
        return new Mark(mark);
    }

    private int mark;

    private Mark(int mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return String.valueOf(mark);
    }
}
