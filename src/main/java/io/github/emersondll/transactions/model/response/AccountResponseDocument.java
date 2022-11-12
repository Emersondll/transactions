package io.github.emersondll.transactions.model.response;

public class AccountResponseDocument extends AccountResponse {
    public AccountResponseDocument(String accountId, String documentNumber) {
        super(accountId);
        this.documentNumber = documentNumber;
    }

    private String documentNumber;

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
