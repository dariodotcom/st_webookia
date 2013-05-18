package it.webookia.backend.utils.foreignws.isbndb.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TitleLong" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AuthorsText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PublisherText">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="publisher_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="book_id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="isbn" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="isbn13" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "title", "titleLong", "authorsText",
		"publisherText" })
public class BookData {

	@XmlElement(name = "Title", required = true)
	protected String title;
	@XmlElement(name = "TitleLong", required = true)
	protected String titleLong;
	@XmlElement(name = "AuthorsText", required = true)
	protected String authorsText;
	@XmlElement(name = "PublisherText", required = true)
	protected BookData.PublisherText publisherText;
	@XmlAttribute(name = "book_id")
	protected String bookId;
	@XmlAttribute(name = "isbn")
	protected String isbn;
	@XmlAttribute(name = "isbn13")
	protected String isbn13;

	/**
	 * Gets the value of the title property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the value of the title property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTitle(String value) {
		this.title = value;
	}

	/**
	 * Gets the value of the titleLong property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTitleLong() {
		return titleLong;
	}

	/**
	 * Sets the value of the titleLong property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTitleLong(String value) {
		this.titleLong = value;
	}

	/**
	 * Gets the value of the authorsText property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAuthorsText() {
		return authorsText;
	}

	/**
	 * Sets the value of the authorsText property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAuthorsText(String value) {
		this.authorsText = value;
	}

	/**
	 * Gets the value of the publisherText property.
	 * 
	 * @return possible object is {@link BookData.PublisherText }
	 * 
	 */
	public BookData.PublisherText getPublisherText() {
		return publisherText;
	}

	/**
	 * Sets the value of the publisherText property.
	 * 
	 * @param value
	 *            allowed object is {@link BookData.PublisherText }
	 * 
	 */
	public void setPublisherText(BookData.PublisherText value) {
		this.publisherText = value;
	}

	/**
	 * Gets the value of the bookId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBookId() {
		return bookId;
	}

	/**
	 * Sets the value of the bookId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBookId(String value) {
		this.bookId = value;
	}

	/**
	 * Gets the value of the isbn property.
	 * 
	 * @return possible object is {@link Long }
	 * 
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * Sets the value of the isbn property.
	 * 
	 * @param value
	 *            allowed object is {@link Long }
	 * 
	 */
	public void setIsbn(String value) {
		this.isbn = value;
	}

	/**
	 * Gets the value of the isbn13 property.
	 * 
	 * @return possible object is {@link Long }
	 * 
	 */
	public String getIsbn13() {
		return isbn13;
	}

	/**
	 * Sets the value of the isbn13 property.
	 * 
	 * @param value
	 *            allowed object is {@link Long }
	 * 
	 */
	public void setIsbn13(String value) {
		this.isbn13 = value;
	}

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;simpleContent>
	 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
	 *       &lt;attribute name="publisher_id" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *     &lt;/extension>
	 *   &lt;/simpleContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "value" })
	public static class PublisherText {

		@XmlValue
		protected String value;
		@XmlAttribute(name = "publisher_id")
		protected String publisherId;

		/**
		 * Gets the value of the value property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Sets the value of the value property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * Gets the value of the publisherId property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getPublisherId() {
			return publisherId;
		}

		/**
		 * Sets the value of the publisherId property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setPublisherId(String value) {
			this.publisherId = value;
		}

	}

}