package io.github.emersondll.transactions.repository;

import io.github.emersondll.transactions.document.TransactionsDocument;

import java.util.List;

public interface TransactionsRepositoryCustom {

    List<TransactionsDocument> findAllByAccountId(final String accountId);
}
