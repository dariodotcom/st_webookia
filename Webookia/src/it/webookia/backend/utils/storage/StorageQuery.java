package it.webookia.backend.utils.storage;

import java.util.List;

import org.slim3.datastore.Datastore;

import it.webookia.backend.meta.DetailedBookMeta;
import it.webookia.backend.meta.UserEntityMeta;
import it.webookia.backend.model.DetailedBook;
import it.webookia.backend.model.UserEntity;

/**
 * Class that holds the logic behind queries to the storage so that specific
 * storage implementations can't affect resources.
 */
public class StorageQuery {

    /**
     * Queries the storage for a {@link DetailedBook} given its ISBN code.
     * 
     * @param isbn
     *            - a {@link String} containing the ISBN.
     * @return the {@link DetailedBook} represented by ISBN.
     */
    public static DetailedBook getDetailedBookByISBN(String isbn) {
        DetailedBookMeta book = DetailedBookMeta.get();
        return Datastore.query(book).filter(book.isbn.equal(isbn)).asSingle();
    }

    /**
     * Queries the storage for a {@link UserEntity} given its username.
     * 
     * @param username
     *            - the {@link UserEntity} identifier.
     * @return the {@link UserEntity} which has the given username.
     */
    public static UserEntity getUserByUsername(String username) {
        UserEntityMeta user = UserEntityMeta.get();
        return Datastore
            .query(user)
            .filter(user.userId.equal(username))
            .asSingle();
    }

    /**
     * Queries the storage for a list of {@link UserEntity} given their
     * usernames.
     * 
     * @param usernames
     *            - the usernames of users to retrieve
     * @return a {@link List} of {@link UserEntity}.
     */
    public static List<UserEntity> getUsersByUsername(List<String> usernames) {
        UserEntityMeta user = UserEntityMeta.get();
        return Datastore.query(user).filter(user.userId.in(usernames)).asList();
    }
}
