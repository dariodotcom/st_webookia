package it.webookia.backend.utils.foreignws.facebook;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import com.restfb.Parameter;
import com.sun.jersey.api.client.Client;

import it.webookia.backend.descriptor.UserDescriptor;
import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.storage.StorageQuery;

public class FacebookConnector {

    private final static String appID = "497944510260906";
    private final static String appSecret = "390ee5c96bcac601213ee28cc1915ddb";
    private final static String redirectUri =
        "http://87.4.49.248:8888/authentication/landing";

    private final static Pattern successfulResponsePattern = Pattern
        .compile("access_token=([a-z|A-Z|0-9]+)&expires=([0-9]+)");
    private final static String authEndpoint =
        "https://graph.facebook.com/oauth/access_token?client_id=%s&redirect_uri=%s&client_secret=%s&code=%s";

    // private AccessToken token;
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
     * @throws OAuthException
     *             if authorization fails.
     */
    public static AccessToken performOauthValidation(String oauthCode)
            throws OAuthException {
        Client client = Client.create();
        String url =
            String.format(
                authEndpoint,
                appID,
                redirectUri,
                appSecret,
                oauthCode);
        String response = client.resource(url).get(String.class);
        Matcher matcher = successfulResponsePattern.matcher(response);

        if (matcher.matches()) {
            System.out.println(matcher.group(1).length());
            return AccessToken.create(matcher.group(1));
        } else {
            throw new OAuthException(response);
        }
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
        // this.token = token;
        this.graphAPIClient = new DefaultFacebookClient(token.toString());
    }

    /**
     * Returns the username linked with given access token.
     * 
     * @return the username linked with given access token.
     */
    public String getUsername() {
        User self =
            graphAPIClient.fetchObject(
                "me",
                User.class,
                Parameter.with("fields", "username"));
        return self.getUsername();
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

    public UserDescriptor getUserDescriptor() {
        UserDescriptor descriptor = new UserDescriptor();
        User self = graphAPIClient.fetchObject("me", User.class);
        descriptor.setUsername(self.getUsername());
        descriptor.setName(self.getFirstName());
        descriptor.setSurname(self.getLastName());
        //descriptor.setLocation(self.getLocation().getName());
        return descriptor;
    }

}
