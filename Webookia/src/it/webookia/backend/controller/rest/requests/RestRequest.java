package it.webookia.backend.controller.rest.requests;

import it.webookia.backend.utils.HTMLInputFilter;

public abstract class RestRequest {
    protected final static HTMLInputFilter inputFilter = new HTMLInputFilter();
    
    protected static String filter(String input){
        return inputFilter.filter(input);
    }
}
