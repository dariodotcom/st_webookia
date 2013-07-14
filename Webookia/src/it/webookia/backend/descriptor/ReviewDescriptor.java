package it.webookia.backend.descriptor;

import it.webookia.backend.model.Comment;
import it.webookia.backend.model.Review;
import it.webookia.backend.utils.Settings;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * This class provides a view over the review of a book (released by the owner)
 * and the relative comments (released by other users) and acts like a bridge
 * between model and servlets / rest interface.
 * 
 */
@XmlRootElement
@XmlType(name = "review")
public class ReviewDescriptor implements Descriptor {

    private String date;
    private int mark;
    private String text;
    private List<CommentDescriptor> comments;

    /**
     * Class constructor
     * 
     * @param review
     */
    ReviewDescriptor(Review review) {
        comments = new ArrayList<CommentDescriptor>();
        date = Settings.DATE_FORMAT.format(review.getDate());
        mark = review.getMark().intValue();
        text = review.getText();
        for (Comment comment : review.getComments()) {
            comments.add(new CommentDescriptor(comment));
        }
    }

    @XmlElement(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlElement(name = "mark")
    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @XmlElement(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @XmlElement(name = "comments")
    public List<CommentDescriptor> getComments() {
        return comments;
    }

    public void setComments(List<CommentDescriptor> comments) {
        this.comments = comments;
    }

    @XmlType(name = "comment")
    public static class CommentDescriptor implements Descriptor {

        private String authorId;
        private String date;
        private String text;

        public CommentDescriptor(Comment comment) {
            authorId = comment.getAuthor().getUserId();
            date = Settings.DATE_FORMAT.format(comment.getDate());
            text = comment.getText();
        }

        @XmlElement(name = "authorId")
        public String getAuthorId() {
            return authorId;
        }

        public void setAuthorId(String authorId) {
            this.authorId = authorId;
        }

        @XmlElement(name = "date")
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @XmlElement(name = "text")
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

    }
}
