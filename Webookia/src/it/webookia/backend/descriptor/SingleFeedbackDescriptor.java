package it.webookia.backend.descriptor;

import it.webookia.backend.model.Feedback;
import it.webookia.backend.utils.Settings;
import it.webookia.backend.utils.storage.Mark;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "feedbackDescriptor")
public class SingleFeedbackDescriptor implements Descriptor {
    private int mark;
    private String text;
    private String date;

    /* Descriptor */
    SingleFeedbackDescriptor(Feedback feedback) {
        mark = Mark.valueOf(feedback.getMark());
        text = feedback.getText();
        date = Settings.DATE_FORMAT.format(feedback.getDate());
    }

    @XmlElement(name = "mark")
    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @XmlElement(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @XmlElement(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
