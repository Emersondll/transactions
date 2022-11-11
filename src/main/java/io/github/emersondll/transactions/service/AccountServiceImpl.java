package io.github.emersondll.transactions.service;

import io.github.emersondll.transactions.document.AccountDocument;
import io.github.emersondll.transactions.mapper.AccountMapper;
import io.github.emersondll.transactions.model.request.AccountRequest;
import io.github.emersondll.transactions.model.response.AccountResponse;
import io.github.emersondll.transactions.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AccountRepository repository;
    private AccountMapper mapper;

    public AccountResponse createAccount(final AccountRequest request) {
        log.info("Access method createAccount in Service ");
        AccountResponse response = mapper.convertDocumentToResponse(
                repository.save(mapper.convertRequestToDocument(request)));
        log.info("Finished method createAccount in Service ");
        return response;

    }

}
