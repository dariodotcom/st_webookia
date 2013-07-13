package it.webookia.backend.model;

import it.webookia.backend.utils.storage.Mark;
import it.webookia.backend.utils.storage.Storable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.SortDirection;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.InverseModelRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.Sort;

/**
 * This class manages the review of a book released by its owner.
 * 
 */
@Model(schemaVersion = 1)
public class Review implements Serializable, Storable {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public Review() {
        this.date = new Date();

        this.commentsRef =
            new InverseModelListRef<Comment, Review>(
                Comment.class,
                "reviewRef",
                this,
                new Sort("date", SortDirection.DESCENDING));

        this.reviewedBookRef =
            new InverseModelRef<ConcreteBook, Review>(
                ConcreteBook.class,
                "reviewRef",
                this);
    }

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    // Fields
    @Attribute(lob = true)
    private Mark mark;
    private String text;
    private Date date;

    // Relationships
    @Attribute(persistent = false)
    private InverseModelListRef<Comment, Review> commentsRef;

    @Attribute(persistent = false)
    private InverseModelRef<ConcreteBook, Review> reviewedBookRef;

    // Storable
    @Override
    public String getId() {
        return KeyFactory.keyToString(key);
    }

    // Getters and setters
    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
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

    //Relationships getters and setters 
    public InverseModelListRef<Comment, Review> getCommentsRef() {
        return commentsRef;
    }

    public InverseModelRef<ConcreteBook, Review> getReviewedBookRef() {
        return reviewedBookRef;
    }

    public List<Comment> getComments() {
        return commentsRef.getModelList();
    }

    public ConcreteBook getReviewedBook() {
        return reviewedBookRef.getModel();
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
