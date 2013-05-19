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
import it.webookia.backend.utils.storage.Mark;
import it.webookia.backend.utils.storage.StorageFacade;
import it.webookia.backend.utils.storage.StorageQuery;

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

        DetailedBook details = StorageQuery.getDetailedBookByISBN(isbn);

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
        book.setDetailedBook(details);

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

        BookResource bookResource = new BookResource(book);

        if (!bookResource.canBeSeenBy(requestor)) {
            throw new ResourceException(ResourceErrorType.UNAUTHORIZED_ACTION);
        }

        return bookResource;
    }

    // Book that this instance is managing
    private ConcreteBook decoratedBook;

    // Instance Methods
    BookResource(ConcreteBook book) {
        this.decoratedBook = book;
    }

    // Public methods
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
     * @param intMark
     *            - the review mark.
     * */
    public void addReview(UserResource author, String text, int intMark)
            throws ResourceException {
        UserEntity bookOwner = decoratedBook.getOwner();
        Mark mark;

        if (!author.matches(bookOwner)) {
            throw new ResourceException(
                ResourceErrorType.UNAUTHORIZED_ACTION,
                null);
        }

        try {
            mark = Mark.create(intMark);
        } catch (IllegalArgumentException e) {
            throw new ResourceException(ResourceErrorType.BAD_REQUEST, e);
        }

        Review bookReview = decoratedBook.getReview();
        if (bookReview != null) {
            throw new ResourceException(ResourceErrorType.ALREADY_EXSISTING);
        }

        bookReview = new Review();
        bookReview.setMark(mark);
        bookReview.setText(text);
        reviewStorage.persist(bookReview);

        decoratedBook.setReview(bookReview);
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
        Review bookReview = decoratedBook.getReview();

        if (bookReview == null) {
            throw new ResourceException(ResourceErrorType.NOT_FOUND);
        }

        Comment comment = new Comment();
        comment.setAuthor(author.getEntity());
        comment.setText(text);
        commentStorage.persist(comment);
    }

    // Resource methods
    boolean canBeSeenBy(UserResource user) {
        PrivacyLevel privacy = decoratedBook.getPrivacy();
        UserEntity owner = decoratedBook.getOwner();

        if (privacy.equals(PrivacyLevel.PUBLIC)) {
            return true;
        } else if (user.matches(owner)) {
            return true;
        } else if (privacy.equals(PrivacyLevel.FRIENDS_ONLY)
            && user.isFriendWith(owner)) {
            return true;
        } else {
            return false;
        }
    }

    boolean canBeLentBy(UserResource user) {
        UserEntity owner = decoratedBook.getOwner();
        BookStatus status = decoratedBook.getStatus();

        if (user.matches(owner)) {
            return false;
        } else if (!canBeSeenBy(user)) {
            return false;
        } else if (!status.equals(BookStatus.AVAILABLE)) {
            return false;
        } else {
            return true;
        }

    }

    ConcreteBook getEntity() {
        return decoratedBook;
    }
}
