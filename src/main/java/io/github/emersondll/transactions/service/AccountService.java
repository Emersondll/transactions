package io.github.emersondll.transactions.service;

import io.github.emersondll.transactions.document.AccountDocument;
import io.github.emersondll.transactions.model.request.AccountRequest;
import io.github.emersondll.transactions.model.response.AccountResponse;

public interface AccountService {
    AccountResponse createAccount(final AccountRequest request);

    AccountResponse findById(final String accountId) throws Exception;

    AccountDocument findByDocumentNumber(final String documentNumber);

}
