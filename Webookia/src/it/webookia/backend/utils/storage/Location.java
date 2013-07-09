package it.webookia.backend.utils.storage;

import java.io.Serializable;

/**
 * Represents the location of a user.
 * 
 * @author Chiara
 * 
 */
public class Location implements Serializable {

    private static final long serialVersionUID = 3596168218456779058L;

    private final double latitude, longitude;
    private final String name;

    /**
     * Constructs a new instance, given
     * 
     * @param name
     *            - the location name.
     * @param latitude
     *            - the location latitude.
     * @param longitude
     *            - the location longitude.
     */
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
