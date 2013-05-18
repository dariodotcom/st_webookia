package it.webookia.backend.utils.foreignws.isbndb;

import it.webookia.backend.model.DetailedBook;
import it.webookia.backend.utils.foreignws.isbndb.model.BookData;
import it.webookia.backend.utils.foreignws.isbndb.model.BookList;
import it.webookia.backend.utils.foreignws.isbndb.model.ISBNdb;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/** Class to perform requests to isbndb.com to resolve a book's isbn */
public class IsbnResolver {

    private final String accessKey = "K5GL85FQ";
    private final String serviceURI =
        "http://isbndb.com/api/books.xml?access_key=%s&index1=isbn&value1=%s";

    private String isbn = null;
    private DetailedBook result = null;

    /**
     * Creates a new resolver
     * 
     * @param isbn
     *            - the isbn to resolve.
     * */
    public IsbnResolver(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Resolve the given isbn
     * 
     * @return - the BookDetail containing information about the book whose isbn
     *         was passed in the constructor.
     * @throws IsbnDBException
     *             if the service request has failed.
     * */
    public DetailedBook resolve() throws IsbnDBException {
        if (result != null) {
            return result;
        }

        ISBNdb response = performServiceRequest();

        BookList bookList = response.getBookList();
        if (bookList == null) {
            throw new IsbnDBException("Request returned null element");
        }

        result = new DetailedBook();
        BookData data = bookList.getBookData();

        fillResultData(data);
        return result;
    }

    // Helpers

    private ISBNdb performServiceRequest() throws IsbnDBException {
        JAXBContext context = null;
        Unmarshaller um = null;
        String uri = String.format(serviceURI, accessKey, isbn);

        try {
            context =
                JAXBContext
                    .newInstance("it.mybooksharing.backend.model.book.isbndb");
            um = context.createUnmarshaller();
            return (ISBNdb) um.unmarshal(new URL(uri));
        } catch (JAXBException | MalformedURLException e) {
            String message = "Error while performing request to isbndb.com";
            throw new IsbnDBException(message, e);
        }
    }

    private void fillResultData(BookData data) {
        result.setTitle(data.getTitle());
        String[] authors = data.getAuthorsText().split(",");
        for (String author : authors) {
            String trimmed = author.trim();
            if (!trimmed.equals("")) {
                result.addAuthor(trimmed);
            }
        }
    }
}