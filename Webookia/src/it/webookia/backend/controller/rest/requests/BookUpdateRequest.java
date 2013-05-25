package it.webookia.backend.controller.rest.requests;

import it.webookia.backend.enums.BookStatus;
import it.webookia.backend.enums.PrivacyLevel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "bookUpdateRequest")
public class BookUpdateRequest {

    private BookStatus status;
    private PrivacyLevel privacy;

    @XmlElement(name = "status", nillable = true)
    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    @XmlElement(name = "privacy", nillable = true)
    public PrivacyLevel getPrivacy() {
        return privacy;
    }

    public void setPrivacy(PrivacyLevel level) {
        this.privacy = level;
    }
}
