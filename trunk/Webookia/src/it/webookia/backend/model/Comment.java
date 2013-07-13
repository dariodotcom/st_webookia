package it.webookia.backend.model;

import it.webookia.backend.utils.storage.Storable;

import java.io.Serializable;
import java.util.Date;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

/**
 * This class manages the comments that a user can release on a book review made
 * by another user.
 * 
 */
@Model(schemaVersion = 1)
public class Comment implements Serializable, Storable {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public Comment() {
        this.date = new Date();
        this.reviewRef = new ModelRef<Review>(Review.class); //Relationship
        this.authorRef = new ModelRef<UserEntity>(UserEntity.class); //Relationship
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
    
    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    // Relationship getter and setters
    public ModelRef<Review> getReviewRef() {
        return reviewRef;
    }

    public ModelRef<UserEntity> getAuthorRef() {
        return authorRef;
    }
    
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

    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode()); //key hashing
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
