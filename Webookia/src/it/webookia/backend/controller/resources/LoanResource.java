package it.webookia.backend.controller.resources;

import it.webookia.backend.model.Loan;
import it.webookia.backend.utils.storage.StorageFacade;

public class LoanResource {

    private static StorageFacade<Loan> loanStorage = new StorageFacade<Loan>(
        Loan.class);

    private Loan decoratedLoan;

    public static LoanResource createLoan(String ownedBookId, String requestorId) {
        Loan loan = new Loan();

        // Store loan
        loanStorage.persist(loan);

        return new LoanResource(loan);
    }

    protected LoanResource(Loan loan) {
        this.decoratedLoan = loan;
    }

    public void sendContextMessage(String authorID, String message) {

    }

    Loan getEntity() {
        return decoratedLoan;
    }

}
