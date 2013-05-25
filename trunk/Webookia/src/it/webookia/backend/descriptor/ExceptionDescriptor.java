package it.webookia.backend.descriptor;

import it.webookia.backend.controller.resources.exception.ResourceErrorType;
import it.webookia.backend.controller.resources.exception.ResourceException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "type", "message" })
public class ExceptionDescriptor implements Descriptor {

    private ResourceErrorType type;
    private String message;

    public ExceptionDescriptor(ResourceException e) {
        type = e.getErrorType();
        message = e.getLocalizedMessage();
    }

    @XmlElement(name = "type", required = true)
    public ResourceErrorType getType() {
        return type;
    }

    @XmlElement(name = "message", required = true)
    public String getMessage() {
        return message;
    }
}
