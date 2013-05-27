package it.webookia.backend.descriptor;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;


public class MessageDescriptor implements Descriptor {

    private String authorUsername;
    private Date date;
    private String text;

    @XmlElement(name = "authorUsername")
    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    @XmlElement(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
