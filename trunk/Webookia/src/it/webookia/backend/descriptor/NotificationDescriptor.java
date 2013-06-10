package it.webookia.backend.descriptor;

import it.webookia.backend.enums.NotificationType;
import it.webookia.backend.model.Notification;
import it.webookia.backend.utils.Settings;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "notificationDescriptor")
public class NotificationDescriptor implements Descriptor {

    private String id;
    private NotificationType type;
    private String date;
    private String authorId;
    private String contextId;
    private boolean read;

    /* Constructor */
    NotificationDescriptor(Notification notification) {
        id = notification.getId();
        type = notification.getType();
        date = Settings.DATE_FORMAT.format(notification.getDate());
        authorId = notification.getSender().getId();
        contextId = notification.getTargetId();
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
    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
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
