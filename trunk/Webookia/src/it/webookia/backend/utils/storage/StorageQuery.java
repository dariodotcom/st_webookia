package it.webookia.backend.utils.storage;

import org.slim3.datastore.Datastore;

import it.webookia.backend.meta.DetailedBookMeta;
import it.webookia.backend.model.DetailedBook;

public class StorageQuery {

    public static DetailedBook getDetailedBookByISBN(String isbn) {
        DetailedBookMeta book = DetailedBookMeta.get();
        return Datastore.query(book).filter(book.isbn.equal(isbn)).asSingle();
    }

}
