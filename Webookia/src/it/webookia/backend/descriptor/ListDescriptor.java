package it.webookia.backend.descriptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * This class manages descriptors behaviour with lists of generic elements.
 * 
 * @param <E>
 *            the generic element of a list
 */
@XmlRootElement
@XmlType(name = "list")
public class ListDescriptor<E extends Descriptor> implements Descriptor,
        Iterable<E> {

    private List<E> elements;

    /**
     * Class constructor
     */
    public ListDescriptor() {
        this.elements = new ArrayList<E>();
    }

    @XmlElement(name = "elements")
    public List<E> getList() {
        return elements;
    }

    public void setList(List<E> internalList) {
        this.elements = internalList;
    }

    public void addDescriptor(E descriptor) {
        this.elements.add(descriptor);
    }

    @Override
    public Iterator<E> iterator() {
        return elements.iterator();
    }

    public int size() {
        return elements.size();
    }

}
