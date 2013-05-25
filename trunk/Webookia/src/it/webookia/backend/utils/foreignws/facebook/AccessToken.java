package it.webookia.backend.utils.foreignws.facebook;

import java.io.Serializable;

/**
 * Holds a user's Facebook {@code AccessToken} that is required to access its
 * profile on the social network.
 */
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 8012334240294160332L;

    private String accessToken;

    /**
     * Creates a new {@code AccessToken} instance that holds given access token.
     * 
     * @param accessToken
     *            - the {@code String} representation of the access token to
     *            hold.
     * @return the newly created instance.
     */
    public static AccessToken create(String accessToken) {
        return new AccessToken(accessToken);
    }

    private AccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Returns the {@code String} representation of the hold access token.
     * 
     * @return the {@code String} representation of the access token.
     */
    @Override
    public String toString() {
        return accessToken;
    }
}
