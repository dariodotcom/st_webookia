package it.webookia.backend.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CollectionUtils {

    public static <I, O> List<O> map(List<I> input, Mapper<I, O> mapper) {
        List<O> output = new ArrayList<O>();
        for (I inputElem : input) {
            output.add(mapper.map(inputElem));
        }
        return output;
    }

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

}
