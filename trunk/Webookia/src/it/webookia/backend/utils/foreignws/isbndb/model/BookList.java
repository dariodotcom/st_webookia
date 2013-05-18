package it.webookia.backend.utils.foreignws.isbndb.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

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
 *         &lt;element name="BookData">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="TitleLong" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="AuthorsText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="PublisherText">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="publisher_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="book_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="isbn" type="{http://www.w3.org/2001/XMLSchema}long" />
 *                 &lt;attribute name="isbn13" type="{http://www.w3.org/2001/XMLSchema}long" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="total_results" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *       &lt;attribute name="page_size" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *       &lt;attribute name="page_number" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *       &lt;attribute name="shown_results" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "bookData"
})
public class BookList {

    @XmlElement(name = "BookData", required = true)
    protected BookData bookData;
    @XmlAttribute(name = "total_results")
    protected Byte totalResults;
    @XmlAttribute(name = "page_size")
    protected Byte pageSize;
    @XmlAttribute(name = "page_number")
    protected Byte pageNumber;
    @XmlAttribute(name = "shown_results")
    protected Byte shownResults;

    /**
     * Gets the value of the bookData property.
     * 
     * @return
     *     possible object is
     *     {@link BookData }
     *     
     */
    public BookData getBookData() {
        return bookData;
    }

    /**
     * Sets the value of the bookData property.
     * 
     * @param value
     *     allowed object is
     *     {@link BookData }
     *     
     */
    public void setBookData(BookData value) {
        this.bookData = value;
    }

    /**
     * Gets the value of the totalResults property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getTotalResults() {
        return totalResults;
    }

    /**
     * Sets the value of the totalResults property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setTotalResults(Byte value) {
        this.totalResults = value;
    }

    /**
     * Gets the value of the pageSize property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getPageSize() {
        return pageSize;
    }

    /**
     * Sets the value of the pageSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setPageSize(Byte value) {
        this.pageSize = value;
    }

    /**
     * Gets the value of the pageNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets the value of the pageNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setPageNumber(Byte value) {
        this.pageNumber = value;
    }

    /**
     * Gets the value of the shownResults property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getShownResults() {
        return shownResults;
    }

    /**
     * Sets the value of the shownResults property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setShownResults(Byte value) {
        this.shownResults = value;
    }

}