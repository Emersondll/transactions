package io.github.emersondll.transactions.mapper;

import io.github.emersondll.transactions.document.AccountDocument;
import io.github.emersondll.transactions.model.request.AccountRequest;
import io.github.emersondll.transactions.model.response.AccountResponse;
import io.github.emersondll.transactions.model.response.AccountResponseDocument;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Log4j2
public class AccountMapper {

    public AccountDocument convertRequestToDocument(AccountRequest request) {
        log.info("Access AccountMapper to Convert Data for Document");
        return AccountDocument.builder()
                .accountId(UUID.randomUUID().toString())
                .documentNumber(request.getDocumentNumber())
                .createdAt(DateTime.now())
                .build();
    }

    public AccountResponse convertDocumentToResponse(AccountDocument document) {
        log.info("Access AccountMapper to Convert Data for Response");
        return AccountResponse.builder()
                .accountId(document.getAccountId())
                .build();
    }

    public AccountResponse convertDocumentToResponseComplete(AccountDocument document) {
        log.info("Access AccountMapper to Convert Full Data for Response");
        return new AccountResponseDocument(document.getAccountId(), document.getDocumentNumber());
    }
}
