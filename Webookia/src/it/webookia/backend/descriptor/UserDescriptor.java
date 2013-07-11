package it.webookia.backend.descriptor;

import java.util.List;

import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.storage.Location;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "user", propOrder = { "userId", "name", "surname", "location" })
public class UserDescriptor implements Descriptor {

    private String userId;
    private String name;
    private String surname;
    private LocationDescriptor location;
    private String thumbnail;
    private List<String> friendsIds;

    UserDescriptor(UserEntity user) {
        userId = user.getUserId();
        name = user.getName();
        surname = user.getSurname();
        thumbnail = user.getThumbnailUrl();
        location = new LocationDescriptor(user.getLocation());
        friendsIds = user.getFriendList();
    }

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
    public LocationDescriptor getLocation() {
        return location;
    }

    public void setLocation(LocationDescriptor location) {
        this.location = location;
    }

    @XmlElement(name = "thumbnail")
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnailUrl) {
        this.thumbnail = thumbnailUrl;
    }

    @XmlElement(name = "friendsIds")
    public List<String> getFriendsIds() {
        return friendsIds;
    }

    public void setFriendsIds(List<String> friendsIds) {
        this.friendsIds = friendsIds;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    @XmlType(name = "location")
    public static class LocationDescriptor {
        private double latitude;
        private double longitude;
        private String name;

        private LocationDescriptor(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            name = location.getName();
        }

        @XmlElement(name = "latitude")
        public double getLatitude() {
            return latitude;
        }

        @XmlElement(name = "longitude")
        public double getLongitude() {
            return longitude;
        }

        @XmlElement(name = "name")
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return String.format("%s,%s", latitude, longitude);
        }
    }
}
