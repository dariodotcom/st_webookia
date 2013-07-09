package it.webookia.backend.utils.foreignws.isbndb;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

import it.webookia.backend.model.DetailedBook;
import it.webookia.backend.utils.CollectionUtils;
import it.webookia.backend.utils.foreignws.isbndb.GBooksResponse.VolumeInfo;

public class GBooksIsbnResolver {

    private String isbn;

    public GBooksIsbnResolver(String isbn) {
        this.isbn = isbn;
    }

    public DetailedBook resolve() throws IsbnResolverException {

        Client client = Client.create();
        WebResource res =
            client
                .resource("https://www.googleapis.com/books/v1/volumes?q=isbn:"
                    + isbn);

        VolumeInfo result;

        try {
            result = res.get(GBooksResponse.class).getResult();
        } catch (NullPointerException e) {
            throw new IsbnResolverException(
                "Error retrieving book informations",
                e);
        } catch (UniformInterfaceException e){
            System.err.println(e.getResponse());
            throw new IsbnResolverException("Error performing request to GBooks.",e);
        }

        DetailedBook book = new DetailedBook();
        book.setTitle(result.getTitle());
        book.setAuthors(CollectionUtils.toList(result.getAuthors()));
        book.setIsbn(result.getIsbn());
        book.setPublisher(result.getPublisher());
        book.setThumbnail(result.getThumbnail());
        book.setGBooksLink(result.getPreviewLink());

        return book;
    }
}
