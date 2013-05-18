package it.webookia.backend.model;

import it.webookia.backend.utils.storage.Storable;

import java.io.Serializable;
import java.util.Date;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.SortDirection;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.InverseModelRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.Sort;

@Model(schemaVersion = 1)
public class Review implements Serializable, Storable {

    private static final long serialVersionUID = 1L;

    // Default constructor
    public Review() {
        this.date = new Date();

        this.comments =
            new InverseModelListRef<Comment, Review>(
                Comment.class,
                "review",
                this,
                new Sort("date", SortDirection.DESCENDING));

        this.reviewedBook =
            new InverseModelRef<ConcreteBook, Review>(
                ConcreteBook.class,
                "review",
                this);
    }

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    // Fields
    private int mark;
    private String text;
    private Date date;

    // Relationships
    @Attribute(persistent = false)
    private InverseModelListRef<Comment, Review> comments;

    @Attribute(persistent = false)
    private InverseModelRef<ConcreteBook, Review> reviewedBook;

    // Storable
    @Override
    public String getId() {
        return KeyFactory.keyToString(key);
    }

    // Getters and setters
    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

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

    public InverseModelListRef<Comment, Review> getComments() {
        return comments;
    }

    public InverseModelRef<ConcreteBook, Review> getReviewedBook() {
        return reviewedBook;
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
        Review other = (Review) obj;
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
