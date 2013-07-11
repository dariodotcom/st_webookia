package it.webookia.backend.descriptor;

import it.webookia.backend.enums.BookStatus;
import it.webookia.backend.enums.PrivacyLevel;
import it.webookia.backend.model.ConcreteBook;

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
public class BookDescriptor extends DetailedBookDescriptor {

    private String bookId;
    private String ownerId;
    private BookStatus status;
    private PrivacyLevel privacy;
    private String gBookLink;

    /* Constructor */
    BookDescriptor(ConcreteBook book) {
        super(book.getDetailedBook());

        bookId = book.getId();
        ownerId = book.getOwner().getUserId();
        status = book.getStatus();
        privacy = book.getPrivacy();
        gBookLink = book.getDetailedBook().getGBooksLink();
    }

    @XmlElement(name = "bookId")
    public String getId() {
        return bookId;
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

    @XmlElement(name = "gBookLink")
    public String getGBookLink() {
        return gBookLink;
    }
}
