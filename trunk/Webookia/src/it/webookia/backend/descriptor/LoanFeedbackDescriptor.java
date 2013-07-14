package it.webookia.backend.descriptor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * This class provides a view over a loan feedbacks (the borrower and the owner
 * ones) and acts like a bridge between model and servlets / rest interface.
 * 
 */
@XmlRootElement
@XmlType(name = "feedbackDescriptor")
public class LoanFeedbackDescriptor implements Descriptor {

    private SingleFeedbackDescriptor ownerFeedback;
    private SingleFeedbackDescriptor borrowerFeedback;

    /**
     * Class constructor
     * 
     * @param ownerFeedback
     * @param borrowerFeedback
     */
    LoanFeedbackDescriptor(SingleFeedbackDescriptor ownerFeedback,
            SingleFeedbackDescriptor borrowerFeedback) {
        this.ownerFeedback = ownerFeedback;
        this.borrowerFeedback = borrowerFeedback;
    }

    @XmlElement(name = "ownerFeedback")
    public SingleFeedbackDescriptor getOwnerFeedback() {
        return ownerFeedback;
    }

    public void setOwnerFeedback(SingleFeedbackDescriptor ownerFeedback) {
        this.ownerFeedback = ownerFeedback;
    }

    @XmlElement(name = "borrowerFeedback")
    public SingleFeedbackDescriptor getBorrowerFeedback() {
        return borrowerFeedback;
    }

    public void setBorrowerFeedback(SingleFeedbackDescriptor borrowerFeedback) {
        this.borrowerFeedback = borrowerFeedback;
    }
}
