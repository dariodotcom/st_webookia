package it.webookia.backend.controller.resources;

import java.util.List;

import it.webookia.backend.controller.resources.exception.ResourceErrorType;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.descriptor.BookDescriptor;
import it.webookia.backend.descriptor.DescriptorFactory;
import it.webookia.backend.descriptor.ListDescriptor;
import it.webookia.backend.descriptor.ReviewDescriptor;
import it.webookia.backend.descriptor.UserDescriptor;
import it.webookia.backend.enums.BookStatus;
import it.webookia.backend.enums.NotificationType;
import it.webookia.backend.enums.PrivacyLevel;
import it.webookia.backend.model.Comment;
import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.DetailedBook;
import it.webookia.backend.model.Review;
import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.foreignws.isbndb.IsbnDBException;
import it.webookia.backend.utils.foreignws.isbndb.IsbnResolver;
import it.webookia.backend.utils.servlets.SearchParameters;
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
    public static BookResource createBook(String isbn, UserResource user)
            throws ResourceException {
        // TODO [BOOK] check that user does not already have this book.

        DetailedBook details = StorageQuery.getDetailedBookByISBN(isbn);

        if (details == null) {
            try {
                details = new IsbnResolver(isbn).resolve();
                detailedBookStorage.persist(details);
            } catch (IsbnDBException e) {
                throw new ResourceException(ResourceErrorType.SERVER_FAULT, e);
            }
        }

        ConcreteBook book = new ConcreteBook();
        book.setDetailedBook(details);
        book.setOwner(user.getEntity());
        book.setStatus(BookStatus.getDefault());
        book.setPrivacy(PrivacyLevel.getDefault());

        concreteBookStorage.persist(book);

        return new BookResource(book);
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
            String message = "book %s not found";
            throw new ResourceException(
                ResourceErrorType.NOT_FOUND,
                String.format(message, id));
        }

        BookResource bookResource = new BookResource(book);

        if (!bookResource.canBeSeenBy(requestor)) {
            String message = "not authorized to access requested book";
            throw new ResourceException(
                ResourceErrorType.UNAUTHORIZED_ACTION,
                message);
        }

        return bookResource;
    }

//    public static ListDescriptor<BookDescriptor> lookUp(SearchParameters params) {
//        List<ConcreteBook> books = StorageQuery.lookUpBooks(params);
//        return DescriptorFactory.createBookListDescriptor(books);
//    }
    
    // Book that this instance is managing
    private ConcreteBook decoratedBook;

    // Instance Methods
    BookResource(ConcreteBook book) {
        this.decoratedBook = book;
    }

    // Public methods

    boolean canBeLentTo(UserResource user) {
        UserEntity owner = decoratedBook.getOwner();
        BookStatus status = decoratedBook.getStatus();

        if (user == null) {
            System.out.println("User is null");
            return false;
        } else if (user.matches(owner)) {
            System.out.println("Cannot ask yourself a book");
            return false;
        } else if (!canBeSeenBy(user)) {
            System.out.println("You can't see this book");
            return false;
        } else if (!status.equals(BookStatus.AVAILABLE)) {
            System.out.println("Book is not available");
            return false;
        } else {
            return true;
        }
    }

    public boolean isOwner(UserResource user) {
        if (user == null) {
            return false;
        }
        return user.matches(decoratedBook.getOwner());
    }

    /**
     * Change book status. It is only possible to change the status meanwhile a
     * book is not lent and it can't be changed into lent directly by the user.
     * 
     * @param newStatus
     *            - the new status
     * @throws ResourceException
     *             when trying to change the status illegally.
     * */
    public void changeStatus(BookStatus newStatus) throws ResourceException {
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

        if (author == null || !author.matches(bookOwner)) {
            String message = "Only the author can add a review";
            throw new ResourceException(
                ResourceErrorType.UNAUTHORIZED_ACTION,
                message);
        }

        try {
            mark = Mark.create(intMark);
        } catch (IllegalArgumentException e) {
            throw new ResourceException(ResourceErrorType.BAD_REQUEST, e);
        }

        Review bookReview = decoratedBook.getReview();
        if (bookReview != null) {
            String message = "Book already has a review";
            throw new ResourceException(
                ResourceErrorType.ALREADY_EXSISTING,
                message);
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

        if (author == null) {
            String message = "Login required to post a comment";
            throw new ResourceException(
                ResourceErrorType.UNAUTHORIZED_ACTION,
                message);
        }

        if (bookReview == null) {
            String message = "book doesn't have a review";
            throw new ResourceException(ResourceErrorType.NOT_FOUND, message);
        }

        Comment comment = new Comment();
        comment.setAuthor(author.getEntity());
        comment.setText(text);
        comment.setReview(bookReview);
        commentStorage.persist(comment);

        // Send notification
        UserEntity owner = decoratedBook.getOwner();
        if (!author.matches(owner)) {
            NotificationResource.createNotification(
                new UserResource(owner),
                NotificationType.NEW_REVIEW_COMMENT,
                decoratedBook);
        }
    }

    /**
     * Retrieves a representation of managed book that can be distributed to end
     * users.
     * 
     * @return a {@link BookDescriptor} that describes managed book.
     */
    public BookDescriptor getDescriptor() {
        return DescriptorFactory.createFullBookDescriptor(decoratedBook);
    }

    /**
     * Retrieves a representation of the review of managed book.
     * 
     * @return a {@link ReviewDescriptor} that describes the review of managed
     *         book.
     */
    public ReviewDescriptor getReview() {
        Review review = decoratedBook.getReview();
        return DescriptorFactory.createReviewDescriptor(review);
    }

    /**
     * Retrieves a representation of the owner of managed book.
     * 
     * @return a {@link UserDescriptor} that describes the owner of the managed
     *         book.
     */
    public UserDescriptor getOwner() {
        UserEntity owner = decoratedBook.getOwner();
        return DescriptorFactory.createUserDescriptor(owner);
    }

    // Resource methods
    boolean canBeSeenBy(UserResource user) {
        PrivacyLevel privacy = decoratedBook.getPrivacy();
        UserEntity owner = decoratedBook.getOwner();

        if (privacy.equals(PrivacyLevel.PUBLIC)) {
            return true;
        } else if (user == null) {
            return false;
        } else if (user.matches(owner)) {
            return true;
        } else if (privacy.equals(PrivacyLevel.FRIENDS_ONLY)
            && user.isFriendWith(owner)) {
            return true;
        } else {
            return false;
        }
    }

    ConcreteBook getEntity() {
        return decoratedBook;
    }
}
