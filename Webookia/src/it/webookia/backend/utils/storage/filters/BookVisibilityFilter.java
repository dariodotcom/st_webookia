package it.webookia.backend.utils.storage.filters;

import it.webookia.backend.enums.PrivacyLevel;
import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.UserEntity;

import org.slim3.datastore.AbstractAttributeMeta;
import org.slim3.datastore.AbstractCriterion;
import org.slim3.datastore.InMemoryFilterCriterion;

public class BookVisibilityFilter extends AbstractCriterion implements
        InMemoryFilterCriterion {

    private UserEntity user;

    public BookVisibilityFilter(AbstractAttributeMeta<?, ?> attributeMeta,
            UserEntity user) throws NullPointerException {
        super(attributeMeta);
        this.user = user;
    }

    @Override
    public boolean accept(Object model) {
        if (!(model instanceof ConcreteBook)) {
            throw new IllegalArgumentException(
                "Expected book, got something else");
        }

        ConcreteBook book = (ConcreteBook) model;
        PrivacyLevel privacy = book.getPrivacy();

        if (privacy.equals(PrivacyLevel.PUBLIC)) {
            return true;
        }

        if (user == null) {
            return false;
        }

        if (privacy.equals(PrivacyLevel.PRIVATE)) {
            return false;
        }

        // TODO: check friendships.

        return false;
    }
}
