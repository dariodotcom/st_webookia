package it.webookia.backend.utils.servlets;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

/**
 * Manages search parameters when looking for a book. A book can be search by
 * title, author or isbn.
 * 
 */
public class SearchParameters {

    @ParamName("title")
    private String title;

    @ParamName("authors")
    private String authors;

    @ParamName("isbn")
    private String isbn;

    /**
     * Class constructor that parses a given request and after analysing it
     * fills @{SearchParameters} fields.
     * 
     * @param req
     *            the http request
     * @return @{SearchParameters} the filled fields.
     */
    public static SearchParameters createFrom(HttpServletRequest req) {
        SearchParameters parameters = new SearchParameters();

        for (Field field : SearchParameters.class.getDeclaredFields()) {
            ParamName paramName = field.getAnnotation(ParamName.class);
            if (paramName != null) {
                String param = req.getParameter(paramName.value());
                try {
                    field.set(parameters, param);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return parameters;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getISBN() {
        return isbn;
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface ParamName {
        String value();
    }
}
