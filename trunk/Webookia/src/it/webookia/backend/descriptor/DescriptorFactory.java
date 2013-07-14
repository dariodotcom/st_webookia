package it.webookia.backend.descriptor;

import java.util.List;

import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.DetailedBook;
import it.webookia.backend.model.Feedback;
import it.webookia.backend.model.Loan;
import it.webookia.backend.model.Message;
import it.webookia.backend.model.Notification;
import it.webookia.backend.model.Review;
import it.webookia.backend.model.UserEntity;

/**
 * This class is useful because it manages the creation of all the kinds of
 * descriptor.
 * 
 */
public class DescriptorFactory {

    /* USERS */
    public static UserDescriptor createUserDescriptor(UserEntity user) {
        UserDescriptor descriptor = new UserDescriptor(user);
        return descriptor;
    }

    /* BOOKS */
    public static DetailedBookDescriptor createDetailedBookDescriptor(
            DetailedBook book) {
        return (book == null ? null : new DetailedBookDescriptor(book));
    }

    public static BookDescriptor createConcreteBookDescriptor(ConcreteBook book) {
        return (book == null ? null : new BookDescriptor(book));
    }

    public static ListDescriptor<BookDescriptor> createBookListDescriptor(
            List<ConcreteBook> books) {
        ListDescriptor<BookDescriptor> descriptor =
            new ListDescriptor<BookDescriptor>();
        for (ConcreteBook b : books) {
            descriptor.addDescriptor(createConcreteBookDescriptor(b));
        }
        return descriptor;
    }

    public static ListDescriptor<DetailedBookDescriptor> createDetailedBookListDescriptor(
            List<DetailedBook> input) {
        ListDescriptor<DetailedBookDescriptor> output =
            new ListDescriptor<DetailedBookDescriptor>();
        for (DetailedBook b : input) {
            output.addDescriptor(createDetailedBookDescriptor(b));
        }
        return output;
    }

    public static ReviewDescriptor createReviewDescriptor(Review review) {
        if (review == null) {
            return null;
        }

        return new ReviewDescriptor(review);
    }

    /* LOANS */
    public static LoanDescriptor createLoanDescriptor(Loan loan) {
        if (loan == null) {
            return null;
        }
        return new LoanDescriptor(loan);
    }

    public static ListDescriptor<LoanDescriptor> createLoanListDescriptor(
            List<Loan> input) {
        ListDescriptor<LoanDescriptor> list =
            new ListDescriptor<LoanDescriptor>();

        for (Loan l : input) {
            list.addDescriptor(new LoanDescriptor(l));
        }
        return list;

    }

    public static LoanFeedbackDescriptor createFeedbackDescriptor(Loan loan) {
        SingleFeedbackDescriptor ownerFeedback =
            createSingleFeedbackDescriptor(loan.getOwnerFeedback());
        SingleFeedbackDescriptor borrowerFeedback =
            createSingleFeedbackDescriptor(loan.getBorrowerFeedback());

        return new LoanFeedbackDescriptor(ownerFeedback, borrowerFeedback);
    }

    private static SingleFeedbackDescriptor createSingleFeedbackDescriptor(
            Feedback feedback) {
        if (feedback == null) {
            return null;
        }

        return new SingleFeedbackDescriptor(feedback);
    }

    /* MESSAGES */
    public static ListDescriptor<MessageDescriptor> createMessageList(
            List<Message> inputList) {

        ListDescriptor<MessageDescriptor> list =
            new ListDescriptor<MessageDescriptor>();

        for (Message m : inputList) {
            list.addDescriptor(new MessageDescriptor(m));
        }

        return list;
    }

    public static ListDescriptor<SingleFeedbackDescriptor> createFeedbackListDescriptor(
            List<Feedback> input) {
        ListDescriptor<SingleFeedbackDescriptor> output =
            new ListDescriptor<SingleFeedbackDescriptor>();

        for (Feedback f : input) {
            if (f == null) {
                continue;
            }
            output.addDescriptor(new SingleFeedbackDescriptor(f));
        }

        return output;
    }

    /* NOTIFICATIONS */
    public static Descriptor createNotificationList(List<Notification> input) {
        ListDescriptor<NotificationDescriptor> output =
            new ListDescriptor<NotificationDescriptor>();

        for (Notification d : input) {
            output.addDescriptor(createNotificationDescriptor(d));
        }

        return output;
    }

    private static NotificationDescriptor createNotificationDescriptor(
            Notification notification) {
        return new NotificationDescriptor(notification);
    }

}
