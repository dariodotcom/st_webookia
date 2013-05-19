package it.webookia.backend.utils.storage;

import java.util.List;

import org.slim3.datastore.Datastore;

import it.webookia.backend.meta.DetailedBookMeta;
import it.webookia.backend.meta.UserEntityMeta;
import it.webookia.backend.model.DetailedBook;
import it.webookia.backend.model.UserEntity;

public class StorageQuery {

    public static DetailedBook getDetailedBookByISBN(String isbn) {
        DetailedBookMeta book = DetailedBookMeta.get();
        return Datastore.query(book).filter(book.isbn.equal(isbn)).asSingle();
    }

    public static UserEntity getUserByUsername(String username) {
        UserEntityMeta user = UserEntityMeta.get();
        return Datastore
            .query(user)
            .filter(user.userId.equal(username))
            .asSingle();
    }

    public static List<UserEntity> getFriends(List<String> friendsID) {
        UserEntityMeta user = UserEntityMeta.get();
        return Datastore.query(user).filter(user.userId.in(friendsID)).asList();
    }
}
