package it.webookia.backend.descriptor;

import it.webookia.backend.model.ConcreteBook;
import it.webookia.backend.model.DetailedBook;

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
}
