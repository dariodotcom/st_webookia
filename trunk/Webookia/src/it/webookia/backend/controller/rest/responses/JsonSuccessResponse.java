package it.webookia.backend.controller.rest.responses;

import it.webookia.backend.descriptor.Descriptor;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonSuccessResponse extends JsonResponse {
    public JsonSuccessResponse(Descriptor descriptor) {
        super("success", descriptor);
    }
}
