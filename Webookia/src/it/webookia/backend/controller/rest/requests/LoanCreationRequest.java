package it.webookia.backend.controller.rest.requests;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "loan")
public class LoanCreationRequest {

    private String bookId;

    @XmlElement(name = "bookId", required = true, nillable = false)
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

}
