package it.webookia.backend.utils.storage.filters;

import it.webookia.backend.meta.ConcreteBookMeta;
import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.UserEntity;

import org.slim3.datastore.AbstractCriterion;
import org.slim3.datastore.InMemoryFilterCriterion;

public class BookOwnershipFilter extends AbstractCriterion implements
        InMemoryFilterCriterion {

    private UserEntity owner;

    public BookOwnershipFilter(UserEntity owner) throws NullPointerException {

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
