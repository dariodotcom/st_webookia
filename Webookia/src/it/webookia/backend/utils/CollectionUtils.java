package it.webookia.backend.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Container class that holds some methods to work with Lists.
 */
public class CollectionUtils {

    /**
     * Maps an operation over the elements of a {@link List}
     * 
     * @param input
     *            - the input {@link List}
     * @param mapper
     *            - an instance of {@link Mapper} that contains the operation to
     *            map.
     * @return the {@link List} of mapped elements.
     */
    public static <I, O> List<O> map(List<I> input, Mapper<I, O> mapper) {
        List<O> output = new ArrayList<O>();
        for (I inputElem : input) {
            output.add(mapper.map(inputElem));
        }
        return output;
    }

    /**
     * Joins the {@link String} representation of elements of a given list,
     * dividing with a separator.
     * 
     * @param input
     *            - the {@link List} of elements to join.
     * @param separator
     *            - the separator to place between elements
     * @return - the joined {@link String}
     */
    public static <E extends Object> String join(List<E> input, String separator) {
        StringBuilder result = new StringBuilder();
        Iterator<E> it = input.iterator();

        while (it.hasNext()) {
            result.append(it.next());
            if (separator != null && it.hasNext()) {
                result.append(separator);
            }
        }

        return result.toString();
    }

    /**
     * Converts an array into a {@link List}
     * 
     * @param input
     *            - the array to convert.
     * @return the {@link List} of elements from the array.x
     */
    public static <E> List<E> toList(E[] input) {
        List<E> output = new ArrayList<E>();

        for (E in : input) {
            output.add(in);
        }

        return output;
    }

}
