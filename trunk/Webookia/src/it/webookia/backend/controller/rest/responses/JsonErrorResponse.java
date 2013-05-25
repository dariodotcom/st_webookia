package it.webookia.backend.controller.rest.responses;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.descriptor.ExceptionDescriptor;

@XmlRootElement
@XmlType(name = "error-response")
public class JsonErrorResponse extends JsonResponse {
    public JsonErrorResponse(ResourceException e) {
        super("error", new ExceptionDescriptor(e));
        e.printStackTrace(System.err);
    }
}
