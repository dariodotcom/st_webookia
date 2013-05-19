package it.webookia.backend.model;

import it.webookia.backend.utils.storage.Storable;

import java.io.Serializable;
import java.util.Date;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class Comment implements Serializable, Storable {

    private static final long serialVersionUID = 1L;

    // Default constructor
    public Comment() {
        this.date = new Date();
        this.reviewRef = new ModelRef<Review>(Review.class);
        this.authorRef = new ModelRef<UserEntity>(UserEntity.class);
    }

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    // Fields
    private String text;
    private Date date;

    // Relationships
    private ModelRef<Review> reviewRef;
    private ModelRef<UserEntity> authorRef;

    // Storable
    @Override
    public String getId() {
        return KeyFactory.keyToString(key);
    }

    // Getters and setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ModelRef<Review> getReviewRef() {
        return reviewRef;
    }

    public ModelRef<UserEntity> getAuthorRef() {
        return authorRef;
    }

    // Relationship getter and setters
    public Review getReview() {
        return reviewRef.getModel();
    }

    public void setReview(Review review) {
        reviewRef.setModel(review);
    }

    public UserEntity getAuthor() {
        return authorRef.getModel();
    }

    public void setAuthor(UserEntity author) {
        authorRef.setModel(author);
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
        Comment other = (Comment) obj;
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
