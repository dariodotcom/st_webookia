package it.webookia.backend.descriptor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "user", propOrder = { "userId", "name", "surname", "location" })
public class UserDescriptor implements Descriptor {

    private String userId;
    private String name;
    private String surname;
    private String location;
    private String thumbnail;

    // Setters and Getters
    @XmlElement(name = "userId", required = true)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String username) {
        this.userId = username;
    }

    @XmlElement(name = "name", required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "surname", required = true)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @XmlElement(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    @XmlElement(name = "thumbnail")
    public void setThumbnail(String thumbnailUrl) {
        this.thumbnail = thumbnailUrl;
    }
}
