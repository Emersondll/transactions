package io.github.emersondll.transactions.repository;

import io.github.emersondll.transactions.document.TransactionsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TransactionsRepository extends MongoRepository<TransactionsDocument, String>, TransactionsRepositoryCustom {

    @Query
    List<TransactionsDocument> findAllByAccountId(final String accountId);
}
