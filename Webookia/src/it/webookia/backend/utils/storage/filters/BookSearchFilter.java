package it.webookia.backend.utils.storage.filters;

import it.webookia.backend.meta.ConcreteBookMeta;
import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.storage.PermissionManager;

import org.slim3.datastore.AbstractCriterion;
import org.slim3.datastore.InMemoryFilterCriterion;

import com.google.appengine.api.datastore.Key;

public class BookSearchFilter extends AbstractCriterion implements
        InMemoryFilterCriterion {

    private UserEntity requestor;
    private Key detailKey;

    public BookSearchFilter(UserEntity requestor, Key key) throws NullPointerException {
        super(ConcreteBookMeta.get().key);
        this.requestor = requestor;
        this.detailKey = key;
    }

    @Override
    public boolean accept(Object model) {
        if (!(model instanceof ConcreteBook)) {
            throw new IllegalArgumentException("Expected a Concrete Book");
        }

        ConcreteBook book = (ConcreteBook) model;

        if(!book.getDetailedBook().getKey().equals(detailKey)){
            return false;
        }
        
        return PermissionManager.user(requestor).canBorrow(book);
    }
}
