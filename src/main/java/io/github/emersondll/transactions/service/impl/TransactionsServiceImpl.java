package io.github.emersondll.transactions.service.impl;

import io.github.emersondll.transactions.config.constants.RabbitMqConstants;
import io.github.emersondll.transactions.document.AccountDocument;
import io.github.emersondll.transactions.document.OperationsTypeDocument;
import io.github.emersondll.transactions.document.TransactionsDocument;
import io.github.emersondll.transactions.mapper.TransactionMapper;
import io.github.emersondll.transactions.model.request.TransactionsRequest;
import io.github.emersondll.transactions.model.response.BalanceResponse;
import io.github.emersondll.transactions.model.response.TransactionsResponse;
import io.github.emersondll.transactions.repository.TransactionsRepository;
import io.github.emersondll.transactions.service.AccountService;
import io.github.emersondll.transactions.service.OperationsTypeService;
import io.github.emersondll.transactions.service.RabbitMqService;
import io.github.emersondll.transactions.service.TransactionsService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Log4j2
@AllArgsConstructor
public class TransactionsServiceImpl implements TransactionsService {
    public static final String NEW_TRANSACTION = "New transaction registered with ID ";
    public static final String TYPE = " and operation type: ";
    public static final String VALUE = " and value:  ";
    @Autowired
    private AccountService accountService;

    @Autowired
    private OperationsTypeService typeService;
    @Autowired
    private TransactionsRepository repository;
    @Autowired
    private TransactionMapper mapper;
    @Autowired
    private RabbitMqService mqService;


    public TransactionsResponse createTransaction(final TransactionsRequest request) throws Exception {
        log.info("Start createTransaction");
        if (ObjectUtils.isEmpty(request.getAmount()) || ObjectUtils.isEmpty(request.getOperationTypeId()) || ObjectUtils.isEmpty(request.getAccountId())) {
            log.error("Has Data Missing Transaction");
            throw new NullPointerException("Error");
        }
        OperationsTypeDocument typeDocument = typeService.findById(request.getOperationTypeId());
        accountService.findById(request.getAccountId());
        log.info("Save Data and Response");


        TransactionsDocument document = repository.save(mapper.requestToDocument(validateSignalValues(request, typeDocument)));

        mqService.send(
                getQueueType(typeDocument)
                , NEW_TRANSACTION.concat(document.getTransactionsId())
                        .concat(TYPE).concat(request.getOperationTypeId())
                        .concat(VALUE).concat(request.getAmount().toString()));
        return mapper.documentToResponse(document);

    }

    protected TransactionsRequest validateSignalValues(TransactionsRequest request, OperationsTypeDocument typeDocument) {
        log.info("Start validate Signal in Values");

        if (getQueueType(typeDocument).equals(RabbitMqConstants.PAYMENT)) {
            if (request.getAmount().toString().contains("-")) {
                request.setAmount(new BigDecimal(String.valueOf(request.getAmount().negate())));
                return request;
            }
            return request;

        } else if (!request.getAmount().toString().contains("-")) {
            request.setAmount(new BigDecimal(String.valueOf(request.getAmount().negate())));
            return request;
        }
        log.info("Finished validate Signal in Values");
        return request;

    }

    protected String getQueueType(OperationsTypeDocument typeDocument) {
        log.info("Start validate Queue Type");
        if (typeDocument.getDescription().contains("PAGAMENTO")) {
            log.info("Queue Type PAYMENT");
            return RabbitMqConstants.PAYMENT;
        }
        if (typeDocument.getDescription().contains("SAQUE")) {
            log.info("Queue Type WITHDRAWAL");
            return RabbitMqConstants.WITHDRAWAL;
        }
        log.info("Queue Type PURCHASE");
        return RabbitMqConstants.PURCHASE;

    }

    @Override
    public BalanceResponse recoveryBalance(final String documentNumber) throws SQLDataException {
        log.info("Start Recovery Balance");
        BigDecimal value ;
        List<AccountDocument> accountDocument = accountService.findAllByDocumentNumber(documentNumber);
       // List<TransactionsDocument> transactionsDocument = repository.findAllById(accountDocument);


          /*    accountService.findAllByDocumentNumber(documentNumber)
                .stream()
                .map(
                        document ->
                        {
                            value=   repository.findById(
                                    document.getAccountId())
                                    .filter(document1 -> true)
                                    .get()
                                    .getAmount();
                            return value;


                        }


                );*/





        log.info("Finished Recovery Balance");
        return null;
    }
}
