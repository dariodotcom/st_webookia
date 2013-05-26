package it.webookia.backend.descriptor;

import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.DetailedBook;
import it.webookia.backend.model.Loan;

public class DescriptorFactory {

    public static BookDescriptor createFullBookDescriptor(ConcreteBook book) {
        BookDescriptor descriptor = new BookDescriptor();
        DetailedBook details = book.getDetailedBook();

        descriptor.setId(book.getId());
        descriptor.setOwnerId(book.getOwner().getUserName());
        descriptor.setIsbn(details.getIsbn());
        descriptor.setAuthors(details.getAuthors());
        descriptor.setTitle(details.getTitle());
        descriptor.setPublisher(details.getPublisher());
        descriptor.setThumbnail(details.getThumbnail());
        descriptor.setStatus(book.getStatus());
        descriptor.setPrivacy(book.getPrivacy());

        System.out.println("Book id: " + descriptor.getId());

        return descriptor;
    }

    public static LoanDescriptor createLoanDescriptor(Loan loan) {
        LoanDescriptor descriptor = new LoanDescriptor();
        ConcreteBook lentBook = loan.getLentBook();

        descriptor.setId(loan.getId());
        descriptor.setBookId(loan.getId());
        descriptor.setBorrowerId(loan.getBorrower().getUserName());
        descriptor.setOwnerId(lentBook.getOwner().getUserName());
        descriptor.setStatus(loan.getStatus());
        descriptor.setStartDate(loan.getDate());

        return descriptor;
    }
}
