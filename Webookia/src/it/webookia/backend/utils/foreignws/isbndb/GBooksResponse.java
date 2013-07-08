package it.webookia.backend.utils.foreignws.isbndb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
public class GBooksResponse {

    private Item[] items;

    @XmlElement(name = "items")
    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public VolumeInfo getResult() {
        return items[0].getVolumeInfo();
    }

    @XmlType(name = "item")
    public static class Item {
        private VolumeInfo volumeInfo;

        @XmlElement(name = "volumeInfo")
        public VolumeInfo getVolumeInfo() {
            return volumeInfo;
        }

        public void setVolumeInfo(VolumeInfo volumeInfo) {
            this.volumeInfo = volumeInfo;
        }

    }

    @XmlType(name = "volumeInfo")
    public static class VolumeInfo {
        private String title;
        private String[] authors;
        private String publisher;
        private ImageLinks imageLinks;
        private IndustryIdentifier[] identifiers;
        private String previewLink;

        @XmlElement(name = "previewLink")
        public String getPreviewLink() {
            return previewLink;
        }

        public void setPreviewLink(String previewLink) {
            this.previewLink = previewLink;
        }

        @XmlElement(name = "title")
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @XmlElement(name = "authors")
        public String[] getAuthors() {
            return authors;
        }

        public void setAuthors(String[] authors) {
            this.authors = authors;
        }

        @XmlElement(name = "publisher")
        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        @XmlElement(name = "industryIdentifiers")
        public IndustryIdentifier[] getIdentifiers() {
            return identifiers;
        }

        public void setIdentifiers(IndustryIdentifier[] identifiers) {
            this.identifiers = identifiers;
        }

        @XmlElement(name = "imageLinks")
        public ImageLinks getImageLinks() {
            return imageLinks;
        }

        public void setImageLinks(ImageLinks imageLinks) {
            this.imageLinks = imageLinks;
        }

        public String getIsbn() {
            for (IndustryIdentifier id : identifiers) {
                if (id.type.equals("ISBN_13")) {
                    return id.identifier;
                }
            }
            return null;
        }

        public String getThumbnail() {
            return imageLinks.getThumbnail();
        }
    }

    public static class IndustryIdentifier {
        private String type;
        private String identifier;

        @XmlElement(name = "type")
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @XmlElement(name = "identifier")
        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

    }

    public static class ImageLinks {
        private String thumbnail;

        @XmlElement(name = "thumbnail")
        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }
}
