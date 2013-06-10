package it.webookia.backend.descriptor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "feedbackDescriptor")
public class LoanFeedbackDescriptor implements Descriptor {

    private SingleFeedbackDescriptor ownerFeedback;
    private SingleFeedbackDescriptor borrowerFeedback;

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
