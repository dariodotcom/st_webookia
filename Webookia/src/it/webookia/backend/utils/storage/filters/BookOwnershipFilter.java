package it.webookia.backend.utils.storage.filters;

import it.webookia.backend.meta.ConcreteBookMeta;
import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.UserEntity;

import org.slim3.datastore.AbstractCriterion;
import org.slim3.datastore.InMemoryFilterCriterion;

/**
 * Filter to select all the books owned by a customer.
 */
public class BookOwnershipFilter extends AbstractCriterion implements
        InMemoryFilterCriterion {

    private UserEntity owner;

    /**
     * Constructs a new filter for the books of given user.
     * 
     * @param owner - the customer owner of books.
     */
    public BookOwnershipFilter(UserEntity owner) {
        super(ConcreteBookMeta.get().ownerRef);
        this.owner = owner;
    }

    @Override
    public boolean accept(Object model) {
        if (!(model instanceof ConcreteBook)) {
            throw new IllegalArgumentException("Excpected Book");
        }

        ConcreteBook book = (ConcreteBook) model;
        return book.getOwner().equals(owner);
    }
}
