package it.webookia.backend.utils.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.Filter;
import org.slim3.datastore.FilterCriterion;
import org.slim3.datastore.ModelQuery;

import com.google.appengine.api.datastore.Key;

import it.webookia.backend.meta.ConcreteBookMeta;
import it.webookia.backend.meta.DetailedBookMeta;
import it.webookia.backend.meta.LoanMeta;
import it.webookia.backend.meta.NotificationMeta;
import it.webookia.backend.meta.UserEntityMeta;
import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.DetailedBook;
import it.webookia.backend.model.Loan;
import it.webookia.backend.model.Notification;
import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.Settings;
import it.webookia.backend.utils.foreignws.facebook.FacebookConnector;
import it.webookia.backend.utils.servlets.SearchParameters;

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

    public static List<Loan> getUserReceivedLoans(UserEntity receiver, int page) {
        List<ConcreteBook> books = receiver.getOwnedBooks();
        List<Loan> loans = new ArrayList<Loan>();
        for (ConcreteBook b : books) {
            loans.addAll(b.getLoansRef().getModelList());
        }
        return loans; // TODO do it better :(
    }

    public static List<Loan> getUserSentLoans(UserEntity sender, int page) {
        LoanMeta loan = LoanMeta.get();
        ModelQuery<Loan> query = Datastore.query(loan);
        query.filter(loan.borrowerRef.equal(sender.getKey()));
        if (page > 0) {
            query.offset(page * Settings.LOAN_PAGINATION_SIZE);
        }
        query.limit(Settings.LOAN_PAGINATION_SIZE);
        return query.asList();
    }

    public static List<DetailedBook> lookUpDetailedBook(SearchParameters params) {
        DetailedBookMeta detailedBook = DetailedBookMeta.get();
        ModelQuery<DetailedBook> query = Datastore.query(detailedBook);
        
        if(params.getTitle() != null){
            query.filterInMemory(detailedBook.title.contains(params.getTitle()));
        }
        
        if(params.getAuthors()!= null){
            query.filterInMemory(detailedBook.authors.contains(params.getAuthors()));
        }

        if(params.getISBN()!= null){
            query.filter(detailedBook.isbn.equal(params.getISBN()));
        }
        
        return query.asList();
    }
    
    public static List<ConcreteBook> getConcreteBooksByDetail(DetailedBook detail){
        ConcreteBookMeta concreteBook = ConcreteBookMeta.get();
        ModelQuery<ConcreteBook> query = Datastore.query(concreteBook);
        query.filter(concreteBook.detailedBookRef.equal(detail.getKey()));
        return query.asList();
    }
}
