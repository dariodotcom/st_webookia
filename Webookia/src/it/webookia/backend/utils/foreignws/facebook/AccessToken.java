package it.webookia.backend.utils.foreignws.facebook;

import java.io.Serializable;

public class AccessToken implements Serializable {

    private static final long serialVersionUID = 8012334240294160332L;

    private String accessToken;

    public AccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return accessToken;
    }
}
