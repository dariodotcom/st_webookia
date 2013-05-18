package it.webookia.backend.model;

import it.webookia.backend.enums.BookStatus;
import it.webookia.backend.enums.PrivacyLevel;
import it.webookia.backend.utils.storage.Storable;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class ConcreteBook implements Serializable, Storable {

    private static final long serialVersionUID = 1L;

    // Default constructors
    public ConcreteBook() {
        detailedBook = new ModelRef<DetailedBook>(DetailedBook.class);
        review = new ModelRef<Review>(Review.class);
        owner = new ModelRef<UserEntity>(UserEntity.class);
    }

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    // Fields
    private PrivacyLevel privacy;
    private BookStatus status;

    // Relationship
    private ModelRef<DetailedBook> detailedBook;
    private ModelRef<Review> review;
    private ModelRef<UserEntity> owner;

    // Inverse relationship
    // TODO add loan inverse relationship

    //Storable
    @Override
    public String getId() {
        return KeyFactory.keyToString(key);
    }

    public PrivacyLevel getPrivacy() {
        return privacy;
    }

    public void setPrivacy(PrivacyLevel privacy) {
        this.privacy = privacy;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public ModelRef<DetailedBook> getDetailedBook() {
        return detailedBook;
    }

    public ModelRef<Review> getReview() {
        return review;
    }

    public ModelRef<UserEntity> getOwner() {
        return owner;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
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
        ConcreteBook other = (ConcreteBook) obj;
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
