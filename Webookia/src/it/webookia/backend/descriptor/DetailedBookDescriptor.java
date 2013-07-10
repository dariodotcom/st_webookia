package it.webookia.backend.descriptor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.webookia.backend.model.DetailedBook;
import it.webookia.backend.utils.CollectionUtils;

@XmlRootElement
@XmlType(name = "bookDetailDescriptor")
public class DetailedBookDescriptor implements Descriptor {

    private String detailId;
    private String title;
    private String authors;
    private String isbn;
    private String publisher;
    private String thumbnail;
    private String gBooksLink;

    public DetailedBookDescriptor(DetailedBook detailedBook) {
        detailId = detailedBook.getId();
        isbn = detailedBook.getIsbn();
        authors = CollectionUtils.join(detailedBook.getAuthors(), ", ");
        title = detailedBook.getTitle();
        publisher = detailedBook.getPublisher();
        thumbnail = detailedBook.getThumbnail();
        gBooksLink = detailedBook.getGBooksLink();
    }

    @XmlElement(name = "detailId")
    public String getId() {
        return detailId;
    }

    @XmlElement(name = "isbn")
    public String getIsbn() {
        return isbn;
    }

    @XmlElement(name = "title")
    public String getTitle() {
        return title;
    }

    @XmlElement(name = "authors")
    public String getAuthors() {
        return authors;
    }

    @XmlElement(name = "publisher")
    public String getPublisher() {
        return publisher;
    }

    @XmlElement(name = "thumbnail")
    public String getThumbnail() {
        return thumbnail;
    }

    @XmlElement(name = "gBooksLink")
    public String getGBooksLink() {
        return gBooksLink;
    }

}
