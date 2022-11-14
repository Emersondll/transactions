package io.github.emersondll.transactions.service.impl;

import io.github.emersondll.transactions.document.OperationsTypeDocument;
import io.github.emersondll.transactions.repository.OperationsTypeRepository;
import io.github.emersondll.transactions.service.OperationsTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLDataException;
import java.util.Optional;

@Repository
@Log4j2
@AllArgsConstructor
public class OperationsTypeServiceImpl implements OperationsTypeService {

    @Autowired
    OperationsTypeRepository repository;

    public OperationsTypeDocument findById(final String id) throws SQLDataException {

        log.info("Start findById OperationsTypeDocument ");
        Optional<OperationsTypeDocument> response = repository.findById(id);

        if (response.isEmpty()) {
            log.error("find account by id");
            throw new SQLDataException("Id Not Found");
        }
        log.info("Finished findById OperationsTypeDocument ");
        return response.get();

    }
}
