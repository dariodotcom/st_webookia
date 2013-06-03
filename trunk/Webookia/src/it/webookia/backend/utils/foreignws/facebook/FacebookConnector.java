package it.webookia.backend.utils.foreignws.facebook;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.Page;
import com.restfb.types.User;
import com.restfb.Parameter;
import com.sun.jersey.api.client.Client;

import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.Settings;
import it.webookia.backend.utils.storage.Location;

public class FacebookConnector {

    private final static String oauthDialogPattern =
        "https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s&scope=%s";
    private final static String appID = "497944510260906";
    private final static String SCOPE =
        "user_about_me,user_location,friends_about_me,publish_stream";
    private final static String appSecret = "390ee5c96bcac601213ee28cc1915ddb";
    private final static String redirectUri = Settings.CURRENT_HOST
        + "/authentication/landing";

    private final static Pattern successfulResponsePattern = Pattern
        .compile("access_token=([a-z|A-Z|0-9]+)&expires=([0-9]+)");
    private final static String authEndpoint =
        "https://graph.facebook.com/oauth/access_token?client_id=%s&redirect_uri=%s&client_secret=%s&code=%s";

    // private AccessToken token;
    private FacebookClient graphAPIClient;
    private User self;

    /**
     * Retrieves the url that leads to the Facebook login page
     * 
     * @return the url that leads to the Facebook login page
     */
    public static String getOauthDialogUrl() {
        return String.format(oauthDialogPattern, appID, redirectUri, SCOPE);
    }

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
            return AccessToken.create(matcher.group(1));
        } else {
            throw new OAuthException(response);
        }
    }

    /**
     * Creates a new {@FacebookConnector} instance that can
     * performs operations on the user profile corresponding to given user
     * 
     * @param user
     *            - the {@link UserEntity} whose profile to access.
     * @return a new instance of {@link FacebookConnector}
     */
    public static FacebookConnector forUser(UserEntity user) {
        return new FacebookConnector(user.getToken());
    }

    /**
     * Creates a new {@FacebookConnector} instance that can
     * performs operations on the user profile corresponding to given token
     * 
     * @param token
     *            - the {@link AccessToken} corresponding to the user whose
     *            profile to access.
     * @return a new instance of {@link FacebookConnector}
     */
    public static FacebookConnector forToken(AccessToken token) {
        return new FacebookConnector(token);
    }

    private FacebookConnector(AccessToken token) {
        this.graphAPIClient = new DefaultFacebookClient(token.toString());
        this.self =
            graphAPIClient.fetchObject(
                "me",
                User.class,
                Parameter.with("fields", "first_name,last_name"));
    }

    /**
     * Returns the username linked with given access token.
     * 
     * @return the username linked with given access token.
     */
    public String getUserId() {
        return self.getId();
    }

    /**
     * Retrieves the user first name.
     * 
     * @return the user first name.
     */
    public String getFirstName() {
        return self.getFirstName();
    }

    /**
     * Retrieves the user last name.
     * 
     * @return the user last name.
     */
    public String getLastName() {
        return self.getLastName();
    }

    /**
     * Returns the list of id of managed user's Facebook friends.
     * 
     * @return the list of this user friends.
     */
    public List<String> getFriendIds() {
        Connection<User> friends =
            graphAPIClient.fetchConnection("/me/friends", User.class);

        List<String> friendsId = new ArrayList<String>();
        for (User u : friends.getData()) {
            friendsId.add(u.getId());
        }

        return friendsId;
    }

    public Location getLocation() {
        User user =
            graphAPIClient.fetchObject(
                "me",
                User.class,
                Parameter.with("fields", "location"));
        NamedFacebookType loc = user.getLocation();

        if (loc == null) {
            return null;
        }

        String id = loc.getId();

        Page page = graphAPIClient.fetchObject(id, Page.class);
        com.restfb.types.Location userLocation = page.getLocation();

        return new Location(
            userLocation.getLatitude(),
            userLocation.getLongitude());
    }

    public String getThumbnail() {
        Picture p =
            graphAPIClient.fetchObject(
                "me/picture",
                Picture.class,
                Parameter.with("redirect", false),
                Parameter.with("height", 30),
                Parameter.with("width", 30));
        return p.getUrl();
    }
}
