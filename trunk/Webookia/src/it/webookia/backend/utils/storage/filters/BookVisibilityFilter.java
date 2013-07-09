package it.webookia.backend.utils.storage.filters;

import it.webookia.backend.meta.ConcreteBookMeta;
import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.storage.PermissionManager;

import org.slim3.datastore.AbstractCriterion;
import org.slim3.datastore.InMemoryFilterCriterion;

/**
 * Filter to check that an user can access a concrete book.
 */
public class BookVisibilityFilter extends AbstractCriterion implements
        InMemoryFilterCriterion {

    private UserEntity user;

    /**
     * Constructs a new filter.
     * 
     * @param user
     *            - the user to check the visibility for.
     */
    public BookVisibilityFilter(UserEntity user) {
        super(ConcreteBookMeta.get().key);
        this.user = user;
    }

    @Override
    public boolean accept(Object model) {
        if (!(model instanceof ConcreteBook)) {
            throw new IllegalArgumentException(
                "Expected book, got something else");
        }

        ConcreteBook concreteBook = (ConcreteBook) model;
        return PermissionManager.user(user).canAccess(concreteBook);
    }
}
