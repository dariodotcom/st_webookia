package it.webookia.backend.utils;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {

    public static <I, O> List<O> map(List<I> input, Mapper<I, O> mapper) {
        List<O> output = new ArrayList<O>();
        for (I inputElem : input) {
            output.add(mapper.map(inputElem));
        }
        return output;
    }

}
