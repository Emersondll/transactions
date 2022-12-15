package io.github.emersondll.transactions.controller;

import io.github.emersondll.transactions.model.request.TransactionsRequest;
import io.github.emersondll.transactions.model.response.BalanceResponse;
import io.github.emersondll.transactions.model.response.TransactionsResponse;
import io.github.emersondll.transactions.service.TransactionsService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
@AllArgsConstructor
public class TransactionsController implements BaseController {

    private TransactionsService service;

    @PostMapping("/transactions")
    public ResponseEntity<TransactionsResponse> createTransaction(@RequestBody TransactionsRequest request) throws Exception {
        log.info("Start Transactions Controller");
        TransactionsResponse response = service.createTransaction(request);
        log.info("Finished Transactions Controller");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/transactions/balance/{documentNumber}")
    public ResponseEntity<BalanceResponse> balance(@PathVariable String documentNumber) throws SQLDataException {
        log.info("Start Transactions Balance Controller");
        BalanceResponse response = service.recoveryBalance(documentNumber);
        log.info("Finished Transactions Balance Controller");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
