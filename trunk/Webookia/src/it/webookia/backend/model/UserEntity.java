package it.webookia.backend.model;

import it.webookia.backend.utils.foreignws.facebook.AccessToken;
import it.webookia.backend.utils.storage.Location;
import it.webookia.backend.utils.storage.Storable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.SortDirection;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.Sort;

@Model(schemaVersion = 1)
public class UserEntity implements Serializable, Storable {

    private static final long serialVersionUID = 1L;

    // Fields
    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    @Attribute(lob = true)
    private AccessToken token;

    private String userId;
    private String name;
    private String surname;
    private String thumbnailUrl;
    private String pictureUrl;

    private List<String> friendList;

    @Attribute(lob = true)
    private Location location;

    // Relationships
    @Attribute(persistent = false)
    private InverseModelListRef<ConcreteBook, UserEntity> booksRef;

    @Attribute(persistent = false)
    private InverseModelListRef<Notification, UserEntity> notificationsRef;

    // Default constructor
    public UserEntity() {
        this.booksRef =
            new InverseModelListRef<ConcreteBook, UserEntity>(
                ConcreteBook.class,
                "ownerRef",
                this);

        this.notificationsRef =
            new InverseModelListRef<Notification, UserEntity>(
                Notification.class,
                "receiverRef",
                this,
                new Sort("date", SortDirection.DESCENDING));

        this.friendList = new ArrayList<String>();
    }

    // Storable
    @Override
    public String getId() {
        return KeyFactory.keyToString(key);
    }

    // Getters and setters
    public AccessToken getToken() {
        return token;
    }

    public void setToken(AccessToken token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location userLocation) {
        this.location = userLocation;
    }

    public InverseModelListRef<ConcreteBook, UserEntity> getBooksRef() {
        return booksRef;
    }

    public InverseModelListRef<Notification, UserEntity> getNotificationsRef() {
        return notificationsRef;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getFullName() {
        return String.format("%s %s", name, surname);
    }

    public List<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<String> friendList) {
        this.friendList = friendList;
    }

    // Relationships getters
    public List<ConcreteBook> getOwnedBooks() {
        return booksRef.getModelList();
    }

    public List<Notification> getNotifications() {
        return notificationsRef.getModelList();
    }

    public boolean isFriendWith(UserEntity otherUser) {
        return this.friendList.contains(otherUser.getUserId());
    }

    public void addFriend(UserEntity friend) {
        String friendId = friend.getUserId();

        if (this.friendList.contains(friendId)) {
            throw new IllegalStateException(getUserId()
                + " and "
                + friend.getUserId()
                + "are already friends.");
        }
        
        this.friendList.add(friendId);
    }
    
    public void removeFriend(UserEntity friend){
        String friendId = friend.getUserId();

        if (!this.friendList.contains(friendId)) {
            throw new IllegalStateException(getUserId()
                + " and "
                + friend.getUserId()
                + "are not friends.");
        }
        
        this.friendList.remove(friendId);
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
        UserEntity other = (UserEntity) obj;
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
