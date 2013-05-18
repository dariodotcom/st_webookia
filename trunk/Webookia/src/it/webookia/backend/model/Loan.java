package it.webookia.backend.model;

import it.webookia.backend.enums.LoanStatus;

import java.io.Serializable;
import java.util.Date;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.SortDirection;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;
import org.slim3.datastore.Sort;

@Model(schemaVersion = 1)
public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;

    // Default constructor
    public Loan() {

        borrower = new ModelRef<UserEntity>(UserEntity.class);
        lentBook = new ModelRef<ConcreteBook>(ConcreteBook.class);

        messages =
            new InverseModelListRef<Message, Loan>(
                Message.class,
                "relativeLoan",
                this,
                new Sort("date", SortDirection.DESCENDING));
    }

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    // Fields
    private LoanStatus status;
    private Date date;

    // Relationships
    private ModelRef<ConcreteBook> lentBook;
    private ModelRef<UserEntity> borrower;

    @Attribute(persistent = false)
    private InverseModelListRef<Message, Loan> messages;

    // Getters and setters
    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public InverseModelListRef<Message, Loan> getMessages() {
        return messages;
    }

    public ModelRef<ConcreteBook> getLentBook() {
        return lentBook;
    }

    public ModelRef<UserEntity> getBorrower() {
        return borrower;
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
        Loan other = (Loan) obj;
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
