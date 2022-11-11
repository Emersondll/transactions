package io.github.emersondll.transactions.service;

import io.github.emersondll.transactions.model.request.AccountRequest;
import io.github.emersondll.transactions.model.response.AccountResponse;

public interface AccountService {
     AccountResponse createAccount(final AccountRequest request);

}
