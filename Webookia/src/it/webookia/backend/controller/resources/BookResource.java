package it.webookia.backend.controller.resources;

import it.webookia.backend.controller.resources.exception.ResourceErrorType;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.enums.BookStatus;
import it.webookia.backend.enums.PrivacyLevel;
import it.webookia.backend.model.Comment;
import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.DetailedBook;
import it.webookia.backend.model.Review;
import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.foreignws.isbndb.IsbnDBException;
import it.webookia.backend.utils.foreignws.isbndb.IsbnResolver;
import it.webookia.backend.utils.storage.StorageFacade;

/**
 * Class to manage Book entities
 * */
public class BookResource {

    private static StorageFacade<ConcreteBook> concreteBookStorage =
        new StorageFacade<ConcreteBook>(ConcreteBook.class);
    private static StorageFacade<DetailedBook> detailedBookStorage =
        new StorageFacade<DetailedBook>(DetailedBook.class);
    private static StorageFacade<Review> reviewStorage =
        new StorageFacade<Review>(Review.class);
    private static StorageFacade<Comment> commentStorage =
        new StorageFacade<Comment>(Comment.class);

    // Class methods

    /**
     * Creates a new {@link ConcreteBook} that represents a real book owned by a
     * person and saves it in the database. Book details are retrieved based on
     * the book ISBN.
     * 
     * @param isbn
     *            - the book ISBN.
     * @param user
     *            - the {@link UserResource} representing the book owner.
     * @return the newly created book
     * @throws {@link ResourceException} if the creation fails.
     * */
    public static ConcreteBook createBook(String isbn, UserResource user)
            throws ResourceException {
        // TODO check that user does not already have this book.

        DetailedBook details = detailedBookStorage.get(isbn);

        if (details == null) {
            try {
                details = new IsbnResolver(isbn).resolve();
                detailedBookStorage.persist(details);
            } catch (IsbnDBException e) {
                // TODO - improve error reporting
                throw new ResourceException(ResourceErrorType.SERVER_FAULT, e);
            }
        }

        ConcreteBook book = new ConcreteBook();
        book.getDetailedBook().setModel(details);
        
        concreteBookStorage.persist(book);

        return book;
    }

    /**
     * Retrieves a concrete book from the storage and returns a
     * {@link BookResource} instance to manage it.
     * 
     * @param id
     *            - the book id.
     * @return - an instance of {@link BookResource} to manage the desired book.
     * @throws {@link ResourceException} if book is not found.
     * */
    public static BookResource getBook(String id, UserResource requestor)
            throws ResourceException {
        ConcreteBook book = concreteBookStorage.get(id);

        if (book == null) {
            throw new ResourceException(ResourceErrorType.NOT_FOUND, null);
        }

        UserEntity owner = book.getOwner().getModel();

        if (book.getPrivacy() == PrivacyLevel.PRIVATE
            && !requestor.matches(owner)) {
            throw new ResourceException(ResourceErrorType.UNAUTHORIZED_ACTION);
        }

        // TODO check for friendship in case of privacy set to friends.

        return new BookResource(book);
    }

    // Book that this instance is managing
    private ConcreteBook decoratedBook;

    // Instance Methods
    BookResource(ConcreteBook book) {
        this.decoratedBook = book;
    }

    /**
     * Change book status.
     * 
     * @param newStatus
     *            - the new status
     * */
    public void changeStatus(BookStatus newStatus) {
        decoratedBook.setStatus(newStatus);
        concreteBookStorage.persist(decoratedBook);
    }

    /**
     * Change book privacy.
     * 
     * @param newPrivacy
     *            - the new privacy level.
     * */
    public void changePrivacy(PrivacyLevel newPrivacy) {
        decoratedBook.setPrivacy(newPrivacy);
        concreteBookStorage.persist(decoratedBook);
    }

    /**
     * Adds the owner review to the book.
     * 
     * @param author
     *            - the reviewer, which will be checked against the book owner
     *            to see if they match.
     * @param text
     *            - the review text.
     * @param mark
     *            - the review mark.
     * */
    public void addReview(UserResource author, String text, int mark)
            throws ResourceException {
        UserEntity bookOwner = decoratedBook.getOwner().getModel();
        if (!author.matches(bookOwner)) {
            throw new ResourceException(
                ResourceErrorType.UNAUTHORIZED_ACTION,
                null);
        }

        Review bookReview = decoratedBook.getReview().getModel();
        if (bookReview != null) {
            throw new ResourceException(ResourceErrorType.ALREADY_EXSISTING);
        }

        bookReview = new Review();
        bookReview.setMark(mark);
        bookReview.setText(text);
        reviewStorage.persist(bookReview);

        decoratedBook.getReview().setModel(bookReview);
        concreteBookStorage.persist(decoratedBook);
    }

    /**
     * Adds a comment on the book review;
     * 
     * @param author
     *            - the comment author.
     * @param text
     *            - the comment text. throws {@link ResourceException} when
     *            trying to comment to an unexsisting review.
     * */
    public void addComment(UserResource author, String text)
            throws ResourceException {
        Review bookReview = decoratedBook.getReview().getModel();

        if (bookReview == null) {
            throw new ResourceException(ResourceErrorType.NOT_FOUND);
        }

        Comment comment = new Comment();
        comment.getAuthor().setModel(author.getEntity());
        comment.setText(text);
        commentStorage.persist(comment);
    }
}
