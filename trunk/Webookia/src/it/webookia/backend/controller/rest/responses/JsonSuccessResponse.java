package it.webookia.backend.controller.rest.responses;

import it.webookia.backend.descriptor.Descriptor;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "response")
public class JsonSuccessResponse extends JsonResponse {
    public JsonSuccessResponse(Descriptor descriptor) {
        super("success", descriptor);
    }
}
