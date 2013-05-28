package it.webookia.backend.descriptor;

import java.util.List;

import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.DetailedBook;
import it.webookia.backend.model.Feedback;
import it.webookia.backend.model.Loan;
import it.webookia.backend.model.Message;
import it.webookia.backend.model.UserEntity;

public class DescriptorFactory {

    public static UserDescriptor createUserDescriptor(UserEntity user) {
        UserDescriptor descriptor = new UserDescriptor();

        descriptor.setUserId(user.getUserId());
        descriptor.setName(user.getName());
        descriptor.setSurname(user.getSurname());

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

    private static SingleFeedbackDescriptor createSingleFeedbackDescriptor(
            Feedback feedback) {
        SingleFeedbackDescriptor descriptor = new SingleFeedbackDescriptor();
        descriptor.setMark(feedback.getMark().intValue());
        descriptor.setDate(feedback.getDate());
        descriptor.setText(feedback.getText());
        return descriptor;
    }
}
