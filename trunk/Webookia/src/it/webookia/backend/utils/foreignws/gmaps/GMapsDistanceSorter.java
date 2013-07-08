package it.webookia.backend.utils.foreignws.gmaps;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.utils.storage.Location;

import org.slim3.datastore.InMemorySortCriterion;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class GMapsDistanceSorter implements InMemorySortCriterion {

    public final static String service =
        "http://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&sensor=false";

    private Location reference;
    private Map<ConcreteBook, Float> distanceCache;

    public GMapsDistanceSorter(Location reference) {
        this.reference = reference;
        this.distanceCache = new HashMap<ConcreteBook, Float>();
    }

    @Override
    public int compare(Object model1, Object model2)
            throws IllegalStateException {
        ConcreteBook book1;
        ConcreteBook book2;

        try {
            book1 = (ConcreteBook) model1;
            book2 = (ConcreteBook) model2;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(
                "I was expecting a concrete book");
        }

        return getDistance(book1).compareTo(getDistance(book2));

    }

    private Float getDistance(ConcreteBook b) {
        if (distanceCache.containsKey(b)) {
            return distanceCache.get(b);
        }

        Float distance = distanceTo(b.getOwner().getLocation());
        distanceCache.put(b, distance);
        return distance;
    }

    private float distanceTo(Location otherLocation) {
        Client c = Client.create();
        WebResource res =
            c.resource(String.format(service, reference, otherLocation));
        System.out.println(String.format(service, reference, otherLocation));
        return res.get(GMapsResponse.class).getDistanceInMetres();
    }

    // Jackson classes.
    @XmlRootElement
    public static class GMapsResponse {

        private Row[] rows;

        @XmlElement(name = "rows")
        public Row[] getRows() {
            return rows;
        }

        public void setRows(Row[] rows) {
            this.rows = rows;
        }

        public float getDistanceInMetres() {
            String result[] =
                getRows()[0].getElements()[0]
                    .getDuration()
                    .getText()
                    .split(" ");

            System.out.println(result[1].equals("km"));
            return Float.parseFloat(result[0])
                * (result[1].equals("km") ? 1000 : 1);

        }

        public static class Row {
            private Element[] elements;

            public Element[] getElements() {
                return elements;
            }

            public void setElements(Element[] elements) {
                this.elements = elements;
            }
        }

        public static class Element {
            private Distance distance;

            @XmlElement(name = "distance")
            public Distance getDuration() {
                return distance;
            }

            public void setDuration(Distance duration) {
                this.distance = duration;
            }
        }

        public static class Distance {
            private String text;

            @XmlElement(name = "text")
            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }
}
