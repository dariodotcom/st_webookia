package it.webookia.backend.descriptor;

import java.util.List;

import it.webookia.backend.enums.NotificationType;
import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.DetailedBook;
import it.webookia.backend.model.Feedback;
import it.webookia.backend.model.Loan;
import it.webookia.backend.model.Message;
import it.webookia.backend.model.Notification;
import it.webookia.backend.model.Review;
import it.webookia.backend.model.UserEntity;

public class DescriptorFactory {

    public static UserDescriptor createUserDescriptor(UserEntity user) {
        UserDescriptor descriptor = new UserDescriptor();

        descriptor.setUserId(user.getUserId());
        descriptor.setName(user.getName());
        descriptor.setSurname(user.getSurname());
        descriptor.setThumbnail(user.getThumbnailUrl());
        return descriptor;
    }

    public static BookDescriptor createFullBookDescriptor(ConcreteBook book) {
        BookDescriptor descriptor = new BookDescriptor();
        DetailedBook details = book.getDetailedBook();

        descriptor.setId(book.getId());
        descriptor.setOwnerId(book.getOwner().getUserId());
        descriptor.setIsbn(details.getIsbn());
        descriptor.setAuthors(details.getAuthors());
        descriptor.setTitle(details.getTitle());
        descriptor.setPublisher(details.getPublisher());
        descriptor.setThumbnail(details.getThumbnail());
        descriptor.setStatus(book.getStatus());
        descriptor.setPrivacy(book.getPrivacy());

        return descriptor;
    }

    public static LoanDescriptor createLoanDescriptor(Loan loan) {
        LoanDescriptor descriptor = new LoanDescriptor();
        ConcreteBook lentBook = loan.getLentBook();

        descriptor.setId(loan.getId());
        descriptor.setBookId(loan.getId());
        descriptor.setBorrowerId(loan.getBorrower().getUserId());
        descriptor.setOwnerId(lentBook.getOwner().getUserId());
        descriptor.setStatus(loan.getStatus());
        descriptor.setStartDate(loan.getDate());

        return descriptor;
    }

    public static ListDescriptor<MessageDescriptor> createMessageList(
            List<Message> inputList) {

        ListDescriptor<MessageDescriptor> list =
            new ListDescriptor<MessageDescriptor>();

        for (Message m : inputList) {
            MessageDescriptor d = new MessageDescriptor();
            d.setAuthorUsername(m.getAuthor().getUserId());
            d.setDate(m.getDate());
            d.setText(m.getText());
            list.addDescriptor(d);
        }

        return list;
    }

    public static LoanFeedbackDescriptor createFeedbackDescriptor(
            Feedback ownerFeedback, Feedback borrowerFeedback) {
        LoanFeedbackDescriptor descriptor = new LoanFeedbackDescriptor();

        if (ownerFeedback != null) {
            descriptor
                .setOwnerFeedback(createSingleFeedbackDescriptor(ownerFeedback));
        }

        if (borrowerFeedback != null) {
            descriptor
                .setBorrowerFeedback(createSingleFeedbackDescriptor(borrowerFeedback));
        }

        return descriptor;
    }

    public static Descriptor createNotificationList(List<Notification> input) {
        ListDescriptor<NotificationDescriptor> output =
            new ListDescriptor<NotificationDescriptor>();

        for (Notification d : input) {
            output.addDescriptor(createNotificationDescriptor(d));
        }

        return output;
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

    private static NotificationDescriptor createNotificationDescriptor(
            Notification notification) {
        NotificationDescriptor d = new NotificationDescriptor();

        d.setId(notification.getId());
        d.setType(notification.getType());
        d.setDate(notification.getDate().toString());// TODO format date
        d.setAuthorId(notification.getSender().getUserId());
        d.setContextId(notification.getTargetId());
        d.setRead(notification.isRead());

        NotificationType type = notification.getType();
        if (type.equals(NotificationType.NEW_REVIEW_COMMENT)) {
            d.setContextType("BOOK");
        } else {
            d.setContextType("LOAN");
        }

        return d;
    }

    private static SingleFeedbackDescriptor createSingleFeedbackDescriptor(
            Feedback feedback) {
        SingleFeedbackDescriptor descriptor = new SingleFeedbackDescriptor();
        descriptor.setMark(feedback.getMark().intValue());
        descriptor.setDate(feedback.getDate());
        descriptor.setText(feedback.getText());
        return descriptor;
    }

    public static ReviewDescriptor createReviewDescriptor(Review review) {
        if (review == null) {
            return null;
        }

        return new ReviewDescriptor(review);
    }
}
