package it.webookia.backend.utils.storage;

import it.webookia.backend.enums.BookStatus;
import it.webookia.backend.enums.LoanStatus;
import it.webookia.backend.enums.PrivacyLevel;
import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.DetailedBook;
import it.webookia.backend.model.Loan;
import it.webookia.backend.model.UserEntity;
import it.webookia.backend.utils.DebugLogger;

/**
 * This class manages all the permission kinds in the application, for example
 * in books visibility and research.
 * 
 */
public class PermissionManager {

    private static DebugLogger logger = new DebugLogger("Permission Manager");;

    /**
     * Creates a new {@link PermissionManager} for given user.
     * 
     * @param user
     *            - the {@link UserEntity} whose permission we want to check
     * @return
     */
    public static PermissionManager user(UserEntity userEntity) {
        return new PermissionManager(userEntity);
    }

    private UserEntity user;

    /**
     * Class constructor
     * 
     * @param user
     */
    private PermissionManager(UserEntity user) {
        this.user = user;
    }

    /**
     * Checks if an user can access a concrete book. It's the case if: - the
     * book is public, or - the book is for friends only and the user is friend
     * with book owner.
     * 
     * @param concreteBook
     * @return true if the user can access the book.
     */
    public boolean canAccess(ConcreteBook concreteBook) {
        PrivacyLevel privacy = concreteBook.getPrivacy();
        UserEntity owner = concreteBook.getOwner();

        if (privacy.equals(PrivacyLevel.PUBLIC)) {
            return true;
        } else if (user == null) {
            return false;
        } else if (user.equals(owner)) {
            return true;
        } else if (privacy.equals(PrivacyLevel.FRIENDS_ONLY)) {
            return this.user.isFriendWith(owner);
        } else {
            return false;
        }
    }

    /**
     * Checks if user can access given loan. It's the case if he is either the
     * borrower or the lent book owner.
     * 
     * @param loan
     *            - the {@link Loan}
     * @return - true if the user can access the loan
     */
    public boolean canAccess(Loan loan) {
        UserEntity owner = loan.getLentBook().getOwner();
        UserEntity borrower = loan.getBorrower();

        return user.equals(owner) || user.equals(borrower);
    }

    /**
     * Checks if the user can share a book. It's the case if he doesn't have
     * that book shared already.
     * 
     * @param detailedBook
     *            - the {@link DetailedBook} to share.
     * @return true if the user can share it.
     */
    public boolean canShare(DetailedBook detailedBook) {
        for (ConcreteBook book : user.getOwnedBooks()) {
            if (book.getDetailedBook().equals(detailedBook)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if the user can borrow a given book. It's the case if the book is
     * accessible by the user, he's not the owner and the book is available.
     * 
     * @param concreteBook
     *            - the book to borrow.
     * @return
     */
    public boolean canBorrow(ConcreteBook concreteBook) {
        if (user == null) {
            logger.log("Non logged in user cannot borrow books");
            return false;
        }

        if (!canAccess(concreteBook)) {
            return false;
        }

        if (concreteBook.getOwner().equals(user)) {
            logger.log(borrowDeniedMsg("his own book", concreteBook.getId()));
            return false;
        }

        if (concreteBook.getStatus() != BookStatus.AVAILABLE) {
            logger
                .log(borrowDeniedMsg("unavailable book", concreteBook.getId()));
            return false;
        }

        return true;
    }

    /**
     * Checks if an user can review a book. It's the case only if he is the
     * owner and he hasn't already inserted a review.
     * 
     * @param book
     *            - the {@link ConcreteBook} to review.
     * @return true if the user is allowed.
     */
    public boolean canReview(ConcreteBook book) {
        if (!book.getOwner().equals(user) || book.getReview() != null) {
            return false;
        }

        return true;
    }

    /**
     * Checks if an user can comment on a review of a book. It's the case only
     * if he can access the book and the book has a review.
     * 
     * @param book
     *            - the {@link ConcreteBook} to review.
     * @return true if the user is allowed.
     */
    public boolean canCommentOnReview(ConcreteBook book) {
        if (!canAccess(book) || book.getReview() == null) {
            return false;
        }

        return false;
    }

    /**
     * Checks if an user can send a message in a loan. It's the case only if he
     * is involved and the loan.
     * 
     * @param loan
     *            - the loan in which to send the message.
     * @return true if the user is allowed.
     */
    public boolean canSendMessage(Loan loan) {
        if (!canAccess(loan)) {
            return false;
        }

        return true;
    }

    /**
     * Checks if an user can send a feedback in a loan. It's the case only if he
     * is involved and the loan is in status GIVEN_BACK.
     * 
     * @param loan
     *            - the loan in which to send the feedback.
     * @return true if the user is allowed.
     */
    public boolean canSendFeedback(Loan loan) {
        if (!canAccess(loan) || !loan.getStatus().equals(LoanStatus.GIVEN_BACK)) {
            return false;
        }

        return false;
    }

    private String borrowDeniedMsg(String resType, String resId) {
        return String.format(
            "User %s cannot borrow %s %s",
            user.getUserId(),
            resType,
            resId);
    }

}
