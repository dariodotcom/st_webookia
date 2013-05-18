package it.webookia.backend.model;

import it.webookia.backend.enums.LoanStatus;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.InverseModelRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    //Fields
    private LoanStatus status ;
    
    //Relationships
    private ModelRef <Message> sentMsg ;
    private ModelRef <Message> receivedMsg ;
    @Attribute(persistent = false)
    private InverseModelRef <Message, Loan> relativeMsg;
    
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

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public ModelRef <Message> getSentMsg() {
        return sentMsg;
    }

    public void setSentMsg(ModelRef <Message> sentMsg) {
        this.sentMsg = sentMsg;
    }

    public ModelRef <Message> getReceivedMsg() {
        return receivedMsg;
    }

    public void setReceivedMsg(ModelRef <Message> receivedMsg) {
        this.receivedMsg = receivedMsg;
    }

    public InverseModelRef <Message, Loan> getRelativeMsg() {
        return relativeMsg;
    }

    public void setRelativeMsg(InverseModelRef <Message, Loan> relativeMsg) {
        this.relativeMsg = relativeMsg;
    }
}
