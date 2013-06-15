package it.webookia.backend.utils.servlets;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

public class SearchParameters {

    @ParamName("title")
    private String title;

    @ParamName("authors")
    private String authors;

    @ParamName("isbn")
    private String isbn;

    public static SearchParameters createFrom(HttpServletRequest req) {
        SearchParameters parameters = new SearchParameters();

        for (Field field : SearchParameters.class.getDeclaredFields()) {
            ParamName paramName = field.getAnnotation(ParamName.class);
            if (paramName != null) {
                String param = req.getParameter(paramName.value());
                try {
                    field.set(parameters, param);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    System.out.println("Parameter "
                        + paramName
                        + " not found in request");
                    e.printStackTrace();
                }
            }
        }

        return parameters;
    }

    // Getters and setters
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
