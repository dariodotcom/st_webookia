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

    /**
     * Performs the validation of access code against Facebook service and
     * returns the {@link AccessToken} corresponding to the user who has just
     * logged in.
     * 
     * @param oauthCode
     *            - the code given by Facebook after the user has granted us the
     *            right to access his data.
     * @return the {@link AccessToken} corresponding to the user who has just
     *         logged in.
     */
    public static AccessToken performOauthValidation(String oauthCode) {
        // TODO - implement parsing of response
        return null;
    }

    /**
     * Constructs a new {@FacebookConnector} instance that
     * can performs operations on the user profile corresponding to given
     * {@link AccessToken}
     * 
     * @param token
     *            - the {@link AccessToken} of user to handle.
     */
    public FacebookConnector(AccessToken token) {
        this.token = token;
        this.graphAPIClient = new DefaultFacebookClient(token.toString());
    }

    /**
     * Creates a {@link UserEntity} corresponding to the {@link AccessToken}
     * that this instance is managing.
     * 
     * @return the created {@link UserEntity}
     */
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

    /**
     * Returns the list of friends of managed user retrieving friends' usernames
     * from Facebook and then retrieving corresponding {@link UserEntity} from
     * the storage.
     * 
     * @return the list of this user friends.
     */
    public List<UserEntity> getFriends() {
        Connection<User> friends =
            graphAPIClient.fetchConnection("/me/friends", User.class);

        List<String> friendsID = new ArrayList<String>();
        for (User u : friends.getData()) {
            friendsID.add(u.getUsername());
        }

        return StorageQuery.getUsersByUsername(friendsID);
    }

}
