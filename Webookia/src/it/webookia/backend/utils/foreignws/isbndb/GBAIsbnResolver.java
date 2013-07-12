package it.webookia.backend.utils.foreignws.isbndb;

import it.webookia.backend.model.DetailedBook;

public class GBAIsbnResolver {

    private final static String APPLICATION_KEY =
        "AIzaSyAFQbE4wGhLn5FjlwcKcOHHeYMQOBIkDuA";

    private String isbn;

    private GBAIsbnResolver(String isbn) {
        this.isbn = isbn;
    }

    public DetailedBook resolve() {
        
        
        return null;
    }

}
