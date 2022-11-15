package io.github.emersondll.transactions.mapper;

import io.github.emersondll.transactions.document.TransactionsDocument;
import io.github.emersondll.transactions.model.request.TransactionsRequest;
import io.github.emersondll.transactions.model.response.TransactionsResponse;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Log4j2
public class TransactionMapper {

    public TransactionsDocument requestToDocument(TransactionsRequest request) {
        log.info("requetToDocument in Mapper Transaction");
        return TransactionsDocument.builder()
                .accountId(request.getAccountId())
                .amount(request.getAmount())
                .transactionsId(UUID.randomUUID().toString())
                .amount(request.getAmount())
                .eventDate(DateTime.now())
                .operationTypeId(request.getOperationTypeId())
                .build();
    }

    public TransactionsResponse documentToResponse(TransactionsDocument document) {
        log.info("documentToResponse in Mapper Transaction");
        return TransactionsResponse.builder()
                .accountId(document.getAccountId())
                .amount(document.getAmount())
                .transactionsId(document.getTransactionsId())
                .eventDate(DateTime.now())
                .operationTypeId(document.getOperationTypeId())
                .build();
    }
}
