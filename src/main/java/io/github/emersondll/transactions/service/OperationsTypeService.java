package io.github.emersondll.transactions.service;

import io.github.emersondll.transactions.document.OperationsTypeDocument;

import java.sql.SQLDataException;

public interface OperationsTypeService {

    OperationsTypeDocument findById(final String id) throws SQLDataException;
}
