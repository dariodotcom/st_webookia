package it.webookia.backend.utils.storage.filters;

import it.webookia.backend.meta.ConcreteBookMeta;
import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.storage.PermissionManager;

import org.slim3.datastore.AbstractCriterion;
import org.slim3.datastore.InMemoryFilterCriterion;

import com.google.appengine.api.datastore.Key;

/**
 * Filter to select the results of a search for concrete books. It selects only
 * concrete books that are instances of a given detailed and that requestor can
 * borrow. It is implemented using {@link PermissionManager} methods.
 */
public class BookSearchFilter extends AbstractCriterion implements
        InMemoryFilterCriterion {

    private UserEntity requestor;
    private Key detailKey;

    /**
     * Constructs a new filter for given customer.
     * 
     * @param requestor
     *            - the search request sender.
     * @param detailKey
     *            - the detailed book key.
     */
    public BookSearchFilter(UserEntity requestor, Key detailKey) {
        super(ConcreteBookMeta.get().key);
        this.requestor = requestor;
        this.detailKey = detailKey;
    }

    @Override
    public boolean accept(Object model) {
        if (!(model instanceof ConcreteBook)) {
            throw new IllegalArgumentException("Expected a Concrete Book");
        }

        ConcreteBook book = (ConcreteBook) model;

        if (!book.getDetailedBook().getKey().equals(detailKey)) {
            return false;
        }

        return PermissionManager.user(requestor).canBorrow(book);
    }
}
