package it.webookia.backend.utils;

public interface Mapper<I, O> {
    public O map(I input);
}
