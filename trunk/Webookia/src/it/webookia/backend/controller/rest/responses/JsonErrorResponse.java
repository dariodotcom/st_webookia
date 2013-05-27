package it.webookia.backend.controller.rest.responses;

import javax.xml.bind.annotation.XmlRootElement;

import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.descriptor.ExceptionDescriptor;

@XmlRootElement
public class JsonErrorResponse extends JsonResponse {
    public JsonErrorResponse(ResourceException e) {
        super("error", new ExceptionDescriptor(e));
    }
}
