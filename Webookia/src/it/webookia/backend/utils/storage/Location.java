package it.webookia.backend.utils.storage;

import java.io.Serializable;

public class Location implements Serializable {

    private static final long serialVersionUID = 3596168218456779058L;

    private final double latitude, longitude;

    public Location(double latitude, double longitude) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getSerialVersionUID() {
        return serialVersionUID;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
