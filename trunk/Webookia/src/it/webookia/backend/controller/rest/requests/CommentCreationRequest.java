package it.webookia.backend.controller.rest.requests;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "commentCreation")
public class CommentCreationRequest extends RestRequest {

    private String text;

    @XmlElement(name = "text", required = true, nillable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = filter(text);
    }
}
