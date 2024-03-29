package it.webookia.backend.model;

import it.webookia.backend.utils.storage.Mark;
import it.webookia.backend.utils.storage.Storable;

import java.io.Serializable;
import java.util.Date;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

/**
 * This class manages feedback released at the end of the loan between two
 * users.
 * 
 */
@Model(schemaVersion = 1)
public class Feedback implements Serializable, Storable {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public Feedback() {
        this.date = new Date();
    }

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    //Attributes
    @Attribute(lob = true)
    private Mark mark;
    private Date date;
    private String text;
    
    //Storable
    @Override
    public String getId() {
        return KeyFactory.keyToString(key);
    }

    // Getters and setters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
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
        Feedback other = (Feedback) obj;
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
