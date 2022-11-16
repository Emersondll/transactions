package io.github.emersondll.transactions.service;

import io.github.emersondll.transactions.model.request.TransactionsRequest;
import io.github.emersondll.transactions.model.response.BalanceResponse;
import io.github.emersondll.transactions.model.response.TransactionsResponse;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;

@Service
public interface TransactionsService {
    TransactionsResponse createTransaction(final TransactionsRequest request) throws Exception;

    BalanceResponse recoveryBalance(final String documentNumber) throws SQLDataException;
}
