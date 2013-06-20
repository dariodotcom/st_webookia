package it.webookia.backend.controller.rest.requests;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.webookia.backend.enums.PrivacyLevel;

@XmlRootElement
@XmlType(name = "bookCreationRequest")
public class BookCreationRequest extends RestRequest {

    private String isbn;
    private PrivacyLevel privacy;

    @XmlElement(name = "isbn", required = true, nillable = false)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @XmlElement(name = "privacy", nillable = true)
    public PrivacyLevel getPrivacy() {
        return privacy;
    }

    public void setPrivacy(PrivacyLevel privacy) {
        this.privacy = privacy;
    }

}
