package it.webookia.backend.utils;

/**
 * Holder class that contains helper methods for {@link String}s
 */
public class StringUtils {

    /**
     * A {@link String} is considered empty when it's <code>null</code> or its
     * <code>length</code> is 0.
     * 
     * @param s
     *            - the {@link String} to check.
     * @return - true if the {@link String} is empty.
     */
    public static boolean isEmpty(String s) {
        return (s == null || s.equals(""));
    }

}
