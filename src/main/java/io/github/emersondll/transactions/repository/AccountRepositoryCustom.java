package io.github.emersondll.transactions.repository;


import io.github.emersondll.transactions.document.AccountDocument;

public interface AccountRepositoryCustom {

    AccountDocument findByDocumentNumber(String documentNumber);

}
