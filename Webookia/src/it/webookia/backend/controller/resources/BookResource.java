package it.webookia.backend.controller.resources;

import java.util.List;

import it.webookia.backend.controller.resources.exception.ResourceErrorType;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.descriptor.BookDescriptor;
import it.webookia.backend.descriptor.DescriptorFactory;
import it.webookia.backend.descriptor.DetailedBookDescriptor;
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
import it.webookia.backend.utils.foreignws.facebook.FacebookConnector;
import it.webookia.backend.utils.foreignws.facebook.FacebookConnector.BookActivity;
import it.webookia.backend.utils.foreignws.isbndb.GoogleBooksIsbnResolver;
import it.webookia.backend.utils.foreignws.isbndb.IsbnResolverException;
import it.webookia.backend.utils.servlets.SearchParameters;
import it.webookia.backend.utils.storage.Mark;
import it.webookia.backend.utils.storage.PermissionManager;
import it.webookia.backend.utils.storage.StorageException;
import it.webookia.backend.utils.storage.StorageFacade;
import it.webookia.backend.utils.storage.StorageQuery;

/**
 * Class to manage book entities.
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

    // Book that this instance is managing
    private ConcreteBook decoratedBook;

    /**
     * Class constructor.
     * 
     * @param book
     */
    BookResource(ConcreteBook book) {
        this.decoratedBook = book;
    }

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
     * @return the newly created book.
     * @throws {@link ResourceException} if the creation fails.
     * */
    public static BookResource createBook(String isbn, UserResource user)
            throws ResourceException {
        assertLoggedIn(user);

        if (!PermissionManager.user(user.getEntity()).canShare(isbn)) {
            throw new ResourceException(ResourceErrorType.ALREADY_OWNED, "");
        }

        // Verifies first if the book is already in the db
        DetailedBook details = StorageQuery.getDetailedBookByISBN(isbn);
        // if not, looks for it on Google Books
        if (details == null) {
            try {
                details = new GoogleBooksIsbnResolver(isbn).resolve();
            } catch (IsbnResolverException e) {
                throw new ResourceException(
                    ResourceErrorType.ISBN_RESOLVER_ERROR,
                    e);
            }
            detailedBookStorage.persist(details);
        }

        ConcreteBook book = new ConcreteBook();
        book.setDetailedBook(details);
        book.setOwner(user.getEntity());
        book.setStatus(BookStatus.getDefault());
        book.setPrivacy(PrivacyLevel.getDefault());

        concreteBookStorage.persist(book);

        // Posts the activity on facebook
        FacebookConnector.forUser(user.getEntity()).postBookActivityStory(
            BookActivity.SHARE,
            book);

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
        ConcreteBook book;

        try {
            book = concreteBookStorage.get(id);
        } catch (StorageException e) {
            throw new ResourceException(ResourceErrorType.NOT_FOUND, e);
        }

        if (book == null) {
            String message = "book %s not found";
            throw new ResourceException(
                ResourceErrorType.NOT_FOUND,
                String.format(message, id));
        }

        BookResource bookResource = new BookResource(book);

        UserEntity reqEntity = requestor == null ? null : requestor.getEntity();

        if (!PermissionManager.user(reqEntity).canAccess(book)) {
            String message = "Not authorized to access requested book";
            throw new ResourceException(
                ResourceErrorType.UNAUTHORIZED_ACTION,
                message);
        }

        return bookResource;
    }

    /**
     * Retrieves a list of detailed books that matches with the inserted
     * reserarch parameters.
     * 
     * @param params
     *            - a list of research parameters including the title, the
     *            authors and the isbn of the book.
     * @return a {@link ListDescriptor<DetailedBookDescriptor>} with all the
     *         matched books.
     */
    public static ListDescriptor<DetailedBookDescriptor> lookupDetailedBooks(
            SearchParameters params) {
        List<DetailedBook> books = StorageQuery.lookUpDetailedBook(params);
        return DescriptorFactory.createDetailedBookListDescriptor(books);
    }

    /**
     * Retrieves a list of concrete books that matches with the required book
     * key
     * 
     * @param detailId
     *            is the book id a user is looking for
     * @param requestor
     *            identifies a user who is searching for a particular book
     * @return {@ListDescritpor} with all the matched books
     * @throws ResourceException
     *             thrown if the required book is not in the db
     */
    public static ListDescriptor<BookDescriptor> lookupConcreteBooks(
            String detailId, UserResource requestor) throws ResourceException {
        DetailedBook detail;

        assertLoggedIn(requestor);

        try {
            detail = detailedBookStorage.get(detailId);
        } catch (StorageException e) {
            throw new ResourceException(ResourceErrorType.NOT_FOUND, e);
        }

        List<ConcreteBook> books =
            StorageQuery
                .getConcreteBooksByDetail(detail, requestor.getEntity());
        return DescriptorFactory.createBookListDescriptor(books);
    }

    /**
     * Verifies if a user is the owner of a concrete book.
     * 
     * @param user
     *            the user to be verified
     * @return true if the user matches with the owner of the concrete book,
     *         false otherwise.
     */
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
    public void changeStatus(BookStatus newStatus, UserResource requestor)
            throws ResourceException {
        if (!isOwner(requestor)) {
            throw new ResourceException(
                ResourceErrorType.UNAUTHORIZED_ACTION,
                "You need to be the book owner to do this");
        }

        if (newStatus == BookStatus.LENT
            || decoratedBook.getStatus() == BookStatus.LENT) {
            throw new ResourceException(
                ResourceErrorType.BAD_REQUEST,
                "Cannot change status from/to lent");
        }

        decoratedBook.setStatus(newStatus);
        concreteBookStorage.persist(decoratedBook);
    }

    /**
     * Changes book privacy.
     * 
     * @param newPrivacy
     *            - the new privacy level.
     * @throws ResourceException
     * */
    public void changePrivacy(PrivacyLevel newPrivacy, UserResource requestor)
            throws ResourceException {
        if (!isOwner(requestor)) {
            throw new ResourceException(
                ResourceErrorType.UNAUTHORIZED_ACTION,
                "You need to be the book owner to do this");
        }
        decoratedBook.setPrivacy(newPrivacy);
        concreteBookStorage.persist(decoratedBook);
    }

    /**
     * 
     * Adds the owner review to the book.
     * 
     * @param author
     *            - the reviewer, which will be checked against the book owner
     *            to see if they match.
     * @param text
     *            - the review text.
     * @param intMark
     *            - the review mark.
     * @throws ResourceException
     *             occurs when there is a unauthorized action or the passed
     *             parameter has a wrong format or a review is already present
     */
    public void addReview(UserResource author, String text, int intMark)
            throws ResourceException {
        Mark mark;

        if (!PermissionManager
            .user(author.getEntity())
            .canReview(decoratedBook)) {
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

        // Post activity on facebook
        FacebookConnector.forUser(author.getEntity()).postBookActivityStory(
            BookActivity.REVIEW,
            decoratedBook);
    }

    /**
     * 
     * Adds a comment on the book review;
     * 
     * @param author
     *            - the comment author.
     * @param text
     *            - the comment text.
     * @throws {@link ResourceException} when trying to comment to an
     *         unexsisting review.
     */
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
            String message = "Book doesn't have a review";
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
                comment);
        }
    }

    /**
     * Retrieves a representation of managed book that can be distributed to end
     * users.
     * 
     * @return a {@link BookDescriptor} that describes managed book.
     */
    public BookDescriptor getDescriptor() {
        return DescriptorFactory.createConcreteBookDescriptor(decoratedBook);
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

    /**
     * Checks if an user can borrow this book.
     * 
     * @param user
     *            - the user
     * @return true if the user can borrow this book.
     */
    public boolean canBeLentBy(UserResource user) {
        if (user == null) {
            return false;
        }

        return PermissionManager
            .user(user.getEntity())
            .canBorrow(decoratedBook);
    }

    /**
     * Retrieves the concrete book associated to the instance of this book.
     * 
     * @return the concrete book
     */
    ConcreteBook getEntity() {
        return decoratedBook;
    }

    /**
     * Verifies whether a user is logged in.
     * 
     * @param user
     *            the user to be verified
     * @throws ResourceException
     *             if the user is not logged in.
     */
    private static void assertLoggedIn(UserResource user)
            throws ResourceException {
        if (user == null) {
            throw new ResourceException(
                ResourceErrorType.NOT_LOGGED_IN,
                "You need to be logged in.");
        }
    }
}
