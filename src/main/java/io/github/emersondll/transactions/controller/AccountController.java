package io.github.emersondll.transactions.controller;

import io.github.emersondll.transactions.model.request.AccountRequest;
import io.github.emersondll.transactions.model.response.AccountResponse;
import io.github.emersondll.transactions.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RestController
@AllArgsConstructor
public class AccountController implements BaseController {

    private AccountService service;

    @PostMapping("/accounts")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest request) {
        log.info("Start Transactions Controller");
        AccountResponse response = service.createAccount(request);
        log.info("Finished Transactions Controller");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountResponse> createAccount(@PathVariable String accountId) throws Exception {
        log.info("Start Transactions Controller");
        AccountResponse response = service.findById(accountId);
        log.info("Finished Transactions Controller");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
