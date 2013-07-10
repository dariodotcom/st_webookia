package it.webookia.backend.descriptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "list")
public class ListDescriptor<E extends Descriptor> implements Descriptor,
        Iterable<E> {

    private List<E> list;

    public ListDescriptor() {
        this.list = new ArrayList<E>();
    }

    @XmlElement(name = "content")
    public List<E> getList() {
        return list;
    }

    public void setList(List<E> internalList) {
        this.list = internalList;
    }

    public void addDescriptor(E descriptor) {
        this.list.add(descriptor);
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }
    
    public int size(){
        return list.size();
    }

}
