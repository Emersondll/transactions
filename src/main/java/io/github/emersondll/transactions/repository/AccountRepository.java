package io.github.emersondll.transactions.repository;

import io.github.emersondll.transactions.document.AccountDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AccountRepository extends MongoRepository<AccountDocument, String>, AccountRepositoryCustom {

    @Query
    AccountDocument findByDocumentNumber(String documentNumber);

    @Query
    List<AccountDocument> findAllByDocumentNumber(String documentNumber);

}
