package io.github.emersondll.transactions.repository;

import io.github.emersondll.transactions.document.TransactionsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionsRepository extends MongoRepository<TransactionsDocument, String> {
}
