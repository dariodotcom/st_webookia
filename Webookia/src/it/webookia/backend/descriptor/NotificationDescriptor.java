package it.webookia.backend.descriptor;

import it.webookia.backend.enums.NotificationType;
import it.webookia.backend.model.Notification;
import it.webookia.backend.utils.Settings;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * This class provides a view over a notification and acts like a bridge between
 * model and servlets / rest interface.
 * 
 */
@XmlRootElement
@XmlType(name = "notificationDescriptor")
public class NotificationDescriptor implements Descriptor {

    private String id;
    private NotificationType type;
    private String date;
    private String author;
    private String contextId;
    private boolean read;

    /**
     * Class constructor
     * 
     * @param notification
     */
    NotificationDescriptor(Notification notification) {
        id = notification.getId();
        type = notification.getType();
        date = Settings.DATE_FORMAT.format(notification.getDate());
        author = notification.getSender().getFullName();
        contextId = notification.getTargetId();
        read = notification.isRead();
    }

    @XmlElement(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name = "type")
    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    @XmlElement(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement(name = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @XmlElement(name = "contextId")
    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    @XmlElement(name = "read")
    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

}
