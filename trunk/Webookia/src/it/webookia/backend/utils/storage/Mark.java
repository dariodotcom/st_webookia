package it.webookia.backend.utils.storage;

import java.io.Serializable;

/**
 * The {@link Mark} class represents a mark that can range between MIN_VALUE and
 * MAX_VALUE.
 * 
 */
public class Mark implements Serializable {

    private static final long serialVersionUID = 3786089801130448034L;
    private static final String exceptionMessage =
        "Mark has to be an integer between %s and %s, given: %s";

    /**
     * Minimal value that marks can have.
     */
    public static final int MIN_VALUE = 0;

    /**
     * Maximal value that marks can have.
     */
    public static final int MAX_VALUE = 5;

    /**
     * Retrieves the integer value of given Mark
     * 
     * @param mark
     *            - the mark
     * @return - the {@link Mark} integer value unless it's null, null in this
     *         case.
     */
    public static Integer valueOf(Mark mark) {
        if (mark == null) {
            return null;
        } else {
            return mark.intValue();
        }
    }

    /**
     * Constructs a new mark given its numerical value. This value can range
     * between MIN_VALUE and MAX_VALUE, otherwise a
     * {@link IllegalArgumentException} will be thrown.
     * 
     * @param mark
     *            - the mark numerical value.
     * @return a new instance of {@link Mark} that holds the given value.
     * @throws IllegalArgumentException
     *             if given value is outside the legal range.
     */
    public static Mark create(int mark) throws IllegalArgumentException {
        if (mark < MIN_VALUE || mark > MAX_VALUE) {
            String message =
                String.format(exceptionMessage, MIN_VALUE, MAX_VALUE, mark);
            throw new IllegalArgumentException(message);
        }
        return new Mark(mark);
    }

    private int mark;

    /**
     * Class constructor
     * 
     * @param mark
     */
    private Mark(int mark) {
        this.mark = mark;
    }

    /**
     * Returns a {@code String} representation of mark.
     * 
     * @return a String representation of mark.
     */
    @Override
    public String toString() {
        return String.valueOf(mark);
    }

    /**
     * Returns the {@code int} value of mark.
     * 
     * @return the integer value of mark.
     */
    public int intValue() {
        return mark;
    }
}
