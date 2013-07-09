package it.webookia.backend.utils;

/**
 * Class to hold an operation with one input.
 * 
 * @param <I>
 *            - the {@link Class} of input elements
 * @param <O>
 *            - the {@link Class} of output elements
 */
public interface Mapper<I, O> {

    /**
     * The operation to implement.
     * 
     * @param input
     *            - the operation input.
     * @return the operation output.
     */
    public O map(I input);
}
