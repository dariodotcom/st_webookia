package it.webookia.backend.model;

import it.webookia.backend.utils.foreignws.facebook.AccessToken;
import it.webookia.backend.utils.storage.Storable;

import java.io.Serializable;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.SortDirection;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.Sort;

@Model(schemaVersion = 1)
public class UserEntity implements Serializable, Storable {

    private static final long serialVersionUID = 1L;

    // Default constructor
    public UserEntity() {
        this.booksRef =
            new InverseModelListRef<ConcreteBook, UserEntity>(
                ConcreteBook.class,
                "owner",
                this);

        this.notificationsRef =
            new InverseModelListRef<Notification, UserEntity>(
                Notification.class,
                "receiver",
                this,
                new Sort("date", SortDirection.DESCENDING));
    }

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    // Fields
    private String userId;
    @Attribute(lob = true)
    private AccessToken token;

    // Relationships
    @Attribute(persistent = false)
    private InverseModelListRef<ConcreteBook, UserEntity> booksRef;

    @Attribute(persistent = false)
    private InverseModelListRef<Notification, UserEntity> notificationsRef;

    // Storable
    @Override
    public String getId() {
        return KeyFactory.keyToString(key);
    }

    // Getters and setters
    public AccessToken getToken() {
        return token;
    }

    public void setToken(AccessToken token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public InverseModelListRef<ConcreteBook, UserEntity> getBooksRef() {
        return booksRef;
    }

    public InverseModelListRef<Notification, UserEntity> getNotificationsRef() {
        return notificationsRef;
    }

    // Relationships getters
    public List<ConcreteBook> getOwnedBooks() {
        return booksRef.getModelList();
    }

    public List<Notification> getNotifications() {
        return notificationsRef.getModelList();
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
