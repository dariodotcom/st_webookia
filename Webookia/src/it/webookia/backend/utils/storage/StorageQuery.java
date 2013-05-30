package it.webookia.backend.utils.storage;

import java.util.List;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

import it.webookia.backend.meta.DetailedBookMeta;
import it.webookia.backend.meta.NotificationMeta;
import it.webookia.backend.meta.UserEntityMeta;
import it.webookia.backend.model.DetailedBook;
import it.webookia.backend.model.Notification;
import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.foreignws.facebook.FacebookConnector;

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
    public static UserEntity getUserById(String username) {
        UserEntityMeta user = UserEntityMeta.get();
        return Datastore
            .query(user)
            .filter(user.userId.equal(username))
            .asSingle();
    }

    /**
     * Queries the storage for the list of {@link UserEntity} whose username is
     * contained on a given list.
     * 
     * @param usernames
     *            - the list of user id's to search for.
     * @return a {@link List} of {@link UserEntity}.
     */
    public static List<UserEntity> getUserFriends(UserEntity user) {
        List<String> ids = FacebookConnector.forUser(user).getFriendIds();
        UserEntityMeta userMeta = UserEntityMeta.get();
        return Datastore
            .query(userMeta)
            .filter(userMeta.userId.in(ids))
            .asList();
    }

    public static List<Notification> getNotificationOf(UserEntity user,
            int limit) {
        Key userKey = user.getKey();
        NotificationMeta notification = NotificationMeta.get();
        return Datastore
            .query(notification)
            .filter(notification.senderRef.equal(userKey))
            .limit(limit)
            .asList();
    }
}
