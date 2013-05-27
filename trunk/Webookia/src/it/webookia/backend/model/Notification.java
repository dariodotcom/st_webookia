package it.webookia.backend.model;

import it.webookia.backend.enums.NotificationType;
import it.webookia.backend.utils.storage.Storable;

import java.io.Serializable;
import java.util.Date;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class Notification implements Serializable, Storable {

    private static final long serialVersionUID = 1L;

    // Default constructor
    public Notification() {
        this.date = new Date();
        this.senderRef = new ModelRef<UserEntity>(UserEntity.class);
        this.receiverRef = new ModelRef<UserEntity>(UserEntity.class);
    }

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    // Fields
    private Date date;
    private NotificationType type;
    private String targetId;
    private boolean read;

    // Relationships
    private ModelRef<UserEntity> senderRef;
    private ModelRef<UserEntity> receiverRef;

    // Storable
    @Override
    public String getId() {
        return KeyFactory.keyToString(key);
    }

    // Getters and Setters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public ModelRef<UserEntity> getSenderRef() {
        return senderRef;
    }

    public ModelRef<UserEntity> getReceiverRef() {
        return receiverRef;
    }

    // Relationships setters and getters
    public UserEntity getReceiver() {
        return receiverRef.getModel();
    }

    public void setReceiver(UserEntity receiver) {
        receiverRef.setModel(receiver);
    }

    public UserEntity getSender() {
        return senderRef.getModel();
    }

    public void setSender(UserEntity sender) {
        senderRef.setModel(sender);
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
        Notification other = (Notification) obj;
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