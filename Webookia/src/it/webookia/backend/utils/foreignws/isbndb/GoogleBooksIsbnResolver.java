package it.webookia.backend.utils.foreignws.isbndb;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volume.VolumeInfo;
import com.google.api.services.books.model.Volume.VolumeInfo.ImageLinks;
import com.google.api.services.books.model.Volumes;
import com.google.api.services.books.BooksRequestInitializer;

import it.webookia.backend.model.DetailedBook;

public class GoogleBooksIsbnResolver {

    private final static String APPLICATION_KEY =
        "AIzaSyD6MjFT4O-ST5_8TiO0xKF9xnznAOWateI";
    private final static String APPLICATION_NAME = "Webookia/1.0";

    private String isbn;

    public GoogleBooksIsbnResolver(String isbn) {
        this.isbn = isbn;
    }

    public DetailedBook resolve() throws IsbnResolverException {
        Books booksRequestor = initializeRequest();
        Volume result = executeQuery(booksRequestor);
        return createBook(result);
    }

    private Books initializeRequest() throws IsbnResolverException {
        HttpTransport transport;
        JsonFactory jsonFactory;
        GoogleClientRequestInitializer requestInitializer;

        try {
            transport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (IOException | GeneralSecurityException e) {
            throw new IsbnResolverException(
                "Error connectiong to google books webservice.",
                e);
        }

        jsonFactory = new JacksonFactory();
        requestInitializer = new BooksRequestInitializer(APPLICATION_KEY);

        return new Books.Builder(transport, jsonFactory, null)
            .setApplicationName(APPLICATION_NAME)
            .setGoogleClientRequestInitializer(requestInitializer)
            .build();
    }

    private Volume executeQuery(Books requestor) throws IsbnResolverException {
        String query = "isbn:" + isbn;
        List<Volume> results;

        try {
            Volumes volumes = requestor
                    .volumes()
                    .list(query)
                    .set("country", "IT")
                    .execute();
            
            results = volumes.getItems();
        } catch (IOException e) {
            throw new IsbnResolverException("Error executing query " + query, e);
        }

        if (results.size() != 1) {
            throw new IsbnResolverException(
                "Query resulted in more (or less) than 1 result: " + results);
        }

        return results.get(0);
    }

    private DetailedBook createBook(Volume result) {
        DetailedBook book = new DetailedBook();

        VolumeInfo infos = result.getVolumeInfo();
        ImageLinks images = infos.getImageLinks();
        
        // Set details
        book.setAuthors(infos.getAuthors());
        book.setGBooksLink(infos.getPreviewLink());
        book.setIsbn(isbn);
        book.setPublisher(infos.getPublisher());
        book.setThumbnail(images == null ? null : images.getThumbnail());
        book.setTitle(infos.getTitle());
        
        return book;
    }
}
