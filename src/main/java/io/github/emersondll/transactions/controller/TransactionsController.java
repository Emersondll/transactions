package io.github.emersondll.transactions.controller;

import io.github.emersondll.transactions.model.response.TransactionsResponse;
import io.github.emersondll.transactions.service.TransactionsService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
@AllArgsConstructor
public class TransactionsController implements BaseController {

  //  private TransactionsService service;

    @RequestMapping("/transactions")
    public ResponseEntity<TransactionsResponse> createTransaction() {
        log.info("Start Transactions Controller");
        //service.createTransaction()
        TransactionsResponse response = new TransactionsResponse();
        log.info("Finished Transactions Controller");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
