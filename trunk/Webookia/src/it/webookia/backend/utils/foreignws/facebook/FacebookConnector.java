package it.webookia.backend.utils.foreignws.facebook;

import java.util.ArrayList;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import com.restfb.Parameter;

import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.storage.StorageQuery;

public class FacebookConnector {

    private AccessToken token;
    private FacebookClient graphAPIClient;

    public FacebookConnector(AccessToken token) {
        this.token = token;
        this.graphAPIClient = new DefaultFacebookClient(token.toString());
    }

    public UserEntity createUserEntity() {
        User self =
            graphAPIClient.fetchObject(
                "me",
                User.class,
                Parameter.with("fields", "username"));

        UserEntity user = new UserEntity();
        user.setToken(token);
        user.setUserId(self.getUsername());

        return user;
    }

    public List<UserEntity> getFriends() {
        Connection<User> friends =
            graphAPIClient.fetchConnection("/me/friends", User.class);

        List<String> friendsID = new ArrayList<String>();
        for (User u : friends.getData()) {
            friendsID.add(u.getUsername());
        }

        return StorageQuery.getFriends(friendsID);
    }

}
