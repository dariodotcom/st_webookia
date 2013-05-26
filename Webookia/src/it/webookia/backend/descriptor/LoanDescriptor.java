package it.webookia.backend.descriptor;

import it.webookia.backend.enums.LoanStatus;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "loanDescriptor", propOrder = {
    "ownerId",
    "borrowerId",
    "bookId",
    "status",
    "startDate" })
public class LoanDescriptor implements Descriptor {

    private String id;
    private String ownerId;
    private String borrowerId;
    private String bookId;
    private LoanStatus status;
    private Date startDate;

    @XmlElement(name = "id")
    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    @XmlElement(name = "ownerId")
    public String getOwnerId() {
        return ownerId;
    }

    void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @XmlElement(name = "borrowerId")
    public String getBorrowerId() {
        return borrowerId;
    }

    void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    @XmlElement(name = "bookId")
    public String getBookId() {
        return bookId;
    }

    void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @XmlElement(name = "status")
    public LoanStatus getStatus() {
        return status;
    }

    void setStatus(LoanStatus status) {
        this.status = status;
    }

    @XmlElement(name = "startDate")
    public Date getStartDate() {
        return startDate;
    }

    void setStartDate(Date date) {
        this.startDate = date;
    }
}
