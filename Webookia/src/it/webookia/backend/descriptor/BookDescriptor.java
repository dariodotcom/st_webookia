package it.webookia.backend.descriptor;

import it.webookia.backend.enums.BookStatus;
import it.webookia.backend.enums.PrivacyLevel;
import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.DetailedBook;
import it.webookia.backend.utils.CollectionUtils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "book", propOrder = {
    "id",
    "title",
    "authors",
    "isbn",
    "publisher",
    "ownerId",
    "status",
    "privacy",
    "thumbnail" })
public class BookDescriptor implements Descriptor {

    private String id;
    private String title;
    private String authors;
    private String isbn;
    private String publisher;
    private String ownerId;
    private BookStatus status;
    private PrivacyLevel privacy;
    private String thumbnail;

    /* Constructor */
    BookDescriptor(ConcreteBook book) {
        DetailedBook details = book.getDetailedBook();

        id = book.getId();
        ownerId = book.getOwner().getUserId();
        isbn = details.getIsbn();
        authors = CollectionUtils.join(details.getAuthors(), ", ");
        title = details.getTitle();
        publisher = details.getPublisher();
        thumbnail = details.getThumbnail();
        status = book.getStatus();
        privacy = book.getPrivacy();
    }

    @XmlElement(name = "id")
    public String getId() {
        return id;
    }

    @XmlElement(name = "status")
    public BookStatus getStatus() {
        return status;
    }

    @XmlElement(name = "privacy")
    public PrivacyLevel getPrivacy() {
        return privacy;
    }

    @XmlElement(name = "ownerId")
    public String getOwnerId() {
        return ownerId;
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

    // Package visible setters
    void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setAuthors(String authors) {
        this.authors = authors;
    }

    void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    void setStatus(BookStatus status) {
        this.status = status;
    }

    void setPrivacy(PrivacyLevel privacy) {
        this.privacy = privacy;
    }

    public void setId(String id) {
        this.id = id;
    }
}
