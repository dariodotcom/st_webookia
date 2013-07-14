package it.webookia.backend.descriptor;

import it.webookia.backend.enums.LoanStatus;
import it.webookia.backend.model.Loan;
import it.webookia.backend.utils.Settings;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * This class provides a view over a loan and acts like a bridge between model
 * and servlets / rest interface.
 * 
 */
@XmlRootElement
@XmlType(name = "loanDescriptor", propOrder = {
    "id",
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
    private String startDate;

    /**
     * Class constructor
     * 
     * @param loan
     */
    LoanDescriptor(Loan loan) {
        this.id = loan.getId();
        this.bookId = loan.getLentBook().getId();
        this.borrowerId = loan.getBorrower().getUserId();
        this.ownerId = loan.getLentBook().getOwner().getUserId();
        this.status = loan.getStatus();
        this.startDate = Settings.DATE_FORMAT.format(loan.getDate());
    }

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
    public String getStartDate() {
        return startDate;
    }

    void setStartDate(String date) {
        this.startDate = date;
    }
}
