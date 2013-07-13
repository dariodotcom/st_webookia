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
 * This class manages messages that can be exchanged by two users relatively to
 * a loan.
 * 
 */
@Model(schemaVersion = 1)
public class Message implements Serializable, Storable {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public Message() {
        this.date = new Date();
        relativeLoanRef = new ModelRef<Loan>(Loan.class);
        authorRef = new ModelRef<UserEntity>(UserEntity.class);
    }

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    // Fields
    private Date date;
    private String text;

    // Relationship
    private ModelRef<Loan> relativeLoanRef;
    private ModelRef<UserEntity> authorRef;

    // Storable
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
    public ModelRef<Loan> getRelativeLoanRef() {
        return relativeLoanRef;
    }

    public ModelRef<UserEntity> getAuthorRef() {
        return authorRef;
    }

    public Loan getRelativeLoan() {
        return relativeLoanRef.getModel();
    }

    public void setLoan(Loan loan) {
        relativeLoanRef.setModel(loan);
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
        Message other = (Message) obj;
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
