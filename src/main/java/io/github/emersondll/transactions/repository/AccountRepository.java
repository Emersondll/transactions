package io.github.emersondll.transactions.repository;

import io.github.emersondll.transactions.document.AccountDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<AccountDocument, String> {
}
