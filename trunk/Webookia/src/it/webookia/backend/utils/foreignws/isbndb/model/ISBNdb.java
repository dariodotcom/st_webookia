//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.14 at 12:23:59 AM CEST 
//


package it.webookia.backend.utils.foreignws.isbndb.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BookList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="BookData">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="TitleLong" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="AuthorsText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="PublisherText">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="publisher_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="book_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="isbn" type="{http://www.w3.org/2001/XMLSchema}long" />
 *                           &lt;attribute name="isbn13" type="{http://www.w3.org/2001/XMLSchema}long" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="total_results" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                 &lt;attribute name="page_size" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                 &lt;attribute name="page_number" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                 &lt;attribute name="shown_results" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="server_time" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "bookList"
})
@XmlRootElement(name = "ISBNdb")
public class ISBNdb {

    @XmlElement(name = "BookList", required = true)
    protected BookList bookList;
    @XmlAttribute(name = "server_time")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar serverTime;

    /**
     * Gets the value of the bookList property.
     * 
     * @return
     *     possible object is
     *     {@link BookList }
     *     
     */
    public BookList getBookList() {
        return bookList;
    }

    /**
     * Sets the value of the bookList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookList }
     *     
     */
    public void setBookList(BookList value) {
        this.bookList = value;
    }

    /**
     * Gets the value of the serverTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getServerTime() {
        return serverTime;
    }

    /**
     * Sets the value of the serverTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setServerTime(XMLGregorianCalendar value) {
        this.serverTime = value;
    }

}