package it.webookia.backend.controller.rest.responses;

import it.webookia.backend.descriptor.Descriptor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "result", "descriptor" })
public abstract class JsonResponse {

    private String result;
    private Descriptor descriptor;

    protected JsonResponse(String result, Descriptor descriptor) {
        this.result = result;
        this.descriptor = descriptor;
    }

    @XmlElement(name = "result")
    public String getResult() {
        return result;
    }

    @XmlElement(name = "descriptor", nillable = false)
    public Descriptor getDescriptor() {
        return descriptor;
    }

}
