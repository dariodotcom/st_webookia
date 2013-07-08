package it.webookia.backend.utils.storage;

import java.io.Serializable;

public class Location implements Serializable {

    private static final long serialVersionUID = 3596168218456779058L;

    private final double latitude, longitude;
    private final String name;

    public Location(String name, double latitude, double longitude) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
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

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s,%s", latitude, longitude);
    }
}
