package io.github.emersondll.transactions.repository;

import io.github.emersondll.transactions.document.OperationsTypeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OperationsTypeRepository extends MongoRepository<OperationsTypeDocument, String> {
}
