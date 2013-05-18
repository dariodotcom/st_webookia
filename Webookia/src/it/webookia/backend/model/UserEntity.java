package it.webookia.backend.model;

import it.webookia.backend.utils.foreignws.facebook.AccessToken;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.SortDirection;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.Sort;

@Model(schemaVersion = 1)
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public UserEntity() {
        this.books =
            new InverseModelListRef<ConcreteBook, UserEntity>(
                ConcreteBook.class,
                "owner",
                this);

        this.notifications =
            new InverseModelListRef<Notification, UserEntity>(
                Notification.class,
                "receiver",
                this,
                new Sort("date", SortDirection.DESCENDING));

        // Initiaize asked loans
    }

    public UserEntity(String userId) {
        this();
        this.key = Datastore.createKey(this.getClass(), userId);
    }

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    // Fields
    @Attribute(lob = true)
    private AccessToken token;

    // Relationships
    @Attribute(persistent = false)
    private InverseModelListRef<ConcreteBook, UserEntity> books;

    @Attribute(persistent = false)
    private InverseModelListRef<Notification, UserEntity> notifications;

    @Attribute(persistent = false)
    private InverseModelListRef<Loan, UserEntity> askedLoans;

    // Getters and setters
    public String getId() {
        return this.key.getName();
    }

    public AccessToken getToken() {
        return token;
    }

    public void setToken(AccessToken token) {
        this.token = token;
    }

    public InverseModelListRef<ConcreteBook, UserEntity> getBooks() {
        return books;
    }

    public InverseModelListRef<Notification, UserEntity> getNotifications() {
        return notifications;
    }

    public InverseModelListRef<Loan, UserEntity> getAskedLoans() {
        return askedLoans;
    }

    /**
     * Returns the key.
     * 
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     * 
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     * 
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     * 
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserEntity other = (UserEntity) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }
}
