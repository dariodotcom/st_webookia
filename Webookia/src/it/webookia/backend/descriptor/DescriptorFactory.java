package it.webookia.backend.descriptor;

import java.util.List;

import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.Feedback;
import it.webookia.backend.model.Loan;
import it.webookia.backend.model.Message;
import it.webookia.backend.model.Notification;
import it.webookia.backend.model.Review;
import it.webookia.backend.model.UserEntity;

public class DescriptorFactory {

    /* USERS */
    public static UserDescriptor createUserDescriptor(UserEntity user) {
        UserDescriptor descriptor = new UserDescriptor();

        descriptor.setUserId(user.getUserId());
        descriptor.setName(user.getName());
        descriptor.setSurname(user.getSurname());
        descriptor.setThumbnail(user.getThumbnailUrl());
        return descriptor;
    }

    /* BOOKS */
    public static BookDescriptor createFullBookDescriptor(ConcreteBook book) {
        BookDescriptor descriptor = new BookDescriptor(book);
        return descriptor;
    }

    public static ListDescriptor<BookDescriptor> createBookListDescriptor(
            List<ConcreteBook> books) {
        ListDescriptor<BookDescriptor> descriptor =
            new ListDescriptor<BookDescriptor>();
        for (ConcreteBook b : books) {
            descriptor.addDescriptor(createFullBookDescriptor(b));
        }
        return descriptor;
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
