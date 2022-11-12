package io.github.emersondll.transactions.service;

import io.github.emersondll.transactions.model.request.TransactionsRequest;
import io.github.emersondll.transactions.model.response.TransactionsResponse;
import org.springframework.stereotype.Service;

@Service
public interface TransactionsService {
    TransactionsResponse createTransaction(final TransactionsRequest request) throws Exception;

}
