package it.webookia.backend.controller.resources;

import it.webookia.backend.controller.resources.exception.ResourceErrorType;
import it.webookia.backend.controller.resources.exception.ResourceException;
import it.webookia.backend.enums.BookStatus;
import it.webookia.backend.enums.LoanStatus;
import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.Feedback;
import it.webookia.backend.model.Loan;
import it.webookia.backend.model.Message;
import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.storage.Mark;
import it.webookia.backend.utils.storage.StorageFacade;

public class LoanResource {

    private static StorageFacade<Loan> loanStorage = new StorageFacade<Loan>(
        Loan.class);
    private static StorageFacade<Message> messageStorage =
        new StorageFacade<Message>(Message.class);
    private static StorageFacade<Feedback> feedbackStorage =
        new StorageFacade<Feedback>(Feedback.class);

    // Class methods
    /**
     * Creates a new loan between a {@link UserEntity} and the owner of a
     * {@link ConcreteBook} at the initial stage.
     * 
     * @param requestor
     *            - the {@link UserResource} which holds the user who requested
     *            the load;
     * @param book
     *            - the {@link BookResource} which holds the requested book.
     * @return a {@link LoanResource} holding the created loan.
     * @throws ResourceException
     *             if an error occurs.
     */
    public static LoanResource createLoan(UserResource requestor,
            BookResource book) throws ResourceException {
        if (!book.canBeSeenBy(requestor)) {
            throw new ResourceException(ResourceErrorType.UNAUTHORIZED_ACTION);
        }

        if (!book.canBeLentBy(requestor)) {
            throw new ResourceException(ResourceErrorType.RESOURCE_UNAVAILABLE);
        }

        Loan loan = new Loan();
        loan.setLentBook(book.getEntity());
        loan.setBorrower(requestor.getEntity());
        loan.setStatus(LoanStatus.INITIAL);

        loanStorage.persist(loan);
        return new LoanResource(loan);
    }

    /**
     * Retrieves a loan from the storage.
     * 
     * @param id
     *            - the loan id.
     * @return a {@link LoanResource} holding the created loan.
     * @throws ResourceException
     *             if no corresponding loan is found.
     */
    public static LoanResource getLoan(String id) throws ResourceException {
        Loan loan = loanStorage.get(id);

        if (loan == null) {
            throw new ResourceException(ResourceErrorType.NOT_FOUND);
        }

        return new LoanResource(loan);
    }

    private Loan decoratedLoan;

    LoanResource(Loan loan) {
        this.decoratedLoan = loan;
    }

    // Public methods
    /**
     * Accepts a loan request. Only the book owner can perform this action. The
     * {@link LoanStatus} is set to ACCEPTED and the {@link BookStatus} is set
     * to LENT, preventing further loans of the same book as long as the book
     * isn't returned.
     * 
     * @param requestor
     *            - a {@link UserResource} that manages the user who took this
     *            action.
     * @throws ResourceException
     *             if an error occurs.
     */
    public void accept(UserResource requestor) throws ResourceException {
        UserEntity bookOwner = decoratedLoan.getLentBook().getOwner();
        ConcreteBook lentBook = decoratedLoan.getLentBook();
        BookResource lentBookRes = new BookResource(lentBook);

        if (!requestor.matches(bookOwner)) {
            throw new ResourceException(ResourceErrorType.UNAUTHORIZED_ACTION);
        }

        assertBookStatus(lentBook, BookStatus.AVAILABLE);
        assertLoanStatus(decoratedLoan, LoanStatus.INITIAL);
        lentBookRes.changeStatus(BookStatus.LENT);
        decoratedLoan.setStatus(LoanStatus.ACCEPTED);
        loanStorage.persist(decoratedLoan);
    }

    /**
     * 
     * Marks that the borrower has received the book. Only the borrower can mark
     * the book as received.
     * 
     * @param requestor
     *            - the {@link UserResource} that manages the user who took this
     *            action.
     * @throws ResourceException
     *             if an error occurs.
     */
    public void bookReceived(UserResource requestor) throws ResourceException {
        UserEntity borrower = decoratedLoan.getBorrower();
        ConcreteBook lentBook = decoratedLoan.getLentBook();

        if (!requestor.matches(borrower)) {
            throw new ResourceException(ResourceErrorType.UNAUTHORIZED_ACTION);
        }

        assertBookStatus(lentBook, BookStatus.LENT);
        assertLoanStatus(decoratedLoan, LoanStatus.ACCEPTED);
        decoratedLoan.setStatus(LoanStatus.SHIPPED);
        loanStorage.persist(decoratedLoan);
    }

    /**
     * Marks that the book has been given back to its owner. Only the owner can
     * perform this action.
     * 
     * @param requestor
     *            - the {@link UserResource} that manages the user who took this
     *            action.
     * @throws ResourceException
     *             if an error occurs.
     */
    public void bookReturned(UserResource requestor) throws ResourceException {
        ConcreteBook lentBook = decoratedLoan.getLentBook();
        UserEntity bookOwner = lentBook.getOwner();

        BookResource lentBookRes = new BookResource(lentBook);

        if (!requestor.matches(bookOwner)) {
            throw new ResourceException(ResourceErrorType.UNAUTHORIZED_ACTION);
        }

        assertBookStatus(lentBook, BookStatus.LENT);
        assertLoanStatus(decoratedLoan, LoanStatus.SHIPPED);
        lentBookRes.changeStatus(BookStatus.AVAILABLE);
        decoratedLoan.setStatus(LoanStatus.GIVEN_BACK);
        loanStorage.persist(decoratedLoan);
    }

    /**
     * Sends a {@link Message} to the other person involved in a loan. Only the
     * borrower and the owner can send messages within a loan.
     * 
     * @param author
     *            - the message author.
     * @param message
     *            - the text of the message.
     * @throws ResourceException
     *             if an error occurs.
     */
    public void sendContextMessage(UserResource author, String message)
            throws ResourceException {
        Message msg = null;
        ConcreteBook lentBook = decoratedLoan.getLentBook();
        UserEntity bookOwner = lentBook.getOwner();

        if (!author.equals(bookOwner)
            || !author.equals(decoratedLoan.getBorrower())) {
            throw new ResourceException(ResourceErrorType.UNAUTHORIZED_ACTION);
        }

        msg = new Message();
        msg.setAuthor(author.getEntity());
        msg.setLoan(decoratedLoan);
        msg.setText(message);
        messageStorage.persist(msg);
    }

    /**
     * Adds a {@link Feedback} to the loan. Only the owner and the borrower
     * involved in a {@link Loan} can add a feedback.
     * 
     * @param requestor
     *            - a {@link UserResource} managing the author of the feedback.
     * @param intMark
     *            - integer representing the mark of the feedback
     * @param text
     *            - the feedback text
     * @throws ResourceException
     *             if an error occurs.
     */
    public void addFeedback(UserResource requestor, int intMark, String text)
            throws ResourceException {
        assertLoanStatus(decoratedLoan, LoanStatus.GIVEN_BACK);
        UserEntity borrower = decoratedLoan.getBorrower();
        UserEntity owner = decoratedLoan.getLentBook().getOwner();
        Mark mark;

        try {
            mark = Mark.create(intMark);
        } catch (IllegalArgumentException e) {
            throw new ResourceException(ResourceErrorType.BAD_REQUEST, e);
        }

        Feedback feedback = new Feedback();
        feedback.setMark(mark);
        feedback.setText(text);

        if (requestor.matches(borrower)) {
            decoratedLoan.setOwnerFeedback(feedback);
        } else if (requestor.matches(owner)) {
            decoratedLoan.setBorrowerFeedback(feedback);
        } else {
            throw new ResourceException(ResourceErrorType.UNAUTHORIZED_ACTION);
        }

        feedbackStorage.persist(feedback);
        loanStorage.persist(decoratedLoan);
    }

    // Resource methods
    Loan getEntity() {
        return decoratedLoan;
    }

    // Helpers
    private void assertBookStatus(ConcreteBook book, BookStatus status)
            throws ResourceException {
        if (!book.getStatus().equals(status)) {
            throw new ResourceException(ResourceErrorType.ILLEGAL_STATE);
        }
    }

    private void assertLoanStatus(Loan loan, LoanStatus status)
            throws ResourceException {
        if (!loan.getStatus().equals(status)) {
            throw new ResourceException(ResourceErrorType.ILLEGAL_STATE);
        }
    }
}
