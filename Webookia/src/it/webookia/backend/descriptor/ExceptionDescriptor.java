package it.webookia.backend.descriptor;

import it.webookia.backend.controller.resources.exception.ResourceErrorType;
import it.webookia.backend.controller.resources.exception.ResourceException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * This class provides a view over an exception and acts like a bridge between
 * model and servlets / rest interface.
 * 
 */
@XmlType(propOrder = { "type", "message" })
public class ExceptionDescriptor implements Descriptor {

    private ResourceErrorType type;
    private String message;

    /**
     * Class constructor
     * 
     * @param e
     */
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
