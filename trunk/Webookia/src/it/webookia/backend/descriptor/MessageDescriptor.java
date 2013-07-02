package it.webookia.backend.descriptor;

import it.webookia.backend.model.Message;
import it.webookia.backend.utils.Settings;

import javax.xml.bind.annotation.XmlElement;

public class MessageDescriptor implements Descriptor {

    private String authorId;
    private String date;
    private String text;

    /* Constructor */
    MessageDescriptor(Message message) {
        authorId = message.getAuthor().getUserId();
        date = Settings.DATE_FORMAT.format(message.getDate());
        text = message.getText();
    }

    @XmlElement(name = "authorId")
    public String getAuthorUserId() {
        return authorId;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorId = authorUsername;
    }

    @XmlElement(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
