package io.github.emersondll.transactions.service.impl;

import io.github.emersondll.transactions.config.constants.RabbitMqConstants;
import io.github.emersondll.transactions.document.OperationsTypeDocument;
import io.github.emersondll.transactions.document.TransactionsDocument;
import io.github.emersondll.transactions.mapper.TransactionMapper;
import io.github.emersondll.transactions.model.request.TransactionsRequest;
import io.github.emersondll.transactions.model.response.TransactionsResponse;
import io.github.emersondll.transactions.repository.TransactionsRepository;
import io.github.emersondll.transactions.service.AccountService;
import io.github.emersondll.transactions.service.OperationsTypeService;
import io.github.emersondll.transactions.service.RabbitMqService;
import io.github.emersondll.transactions.service.TransactionsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Log4j2
public class TransactionsServiceImpl implements TransactionsService {
    public static final String NEW_TRANSACTION = "New transaction registered with ID ";
    public static final String TYPE = " and operation type: ";
    public static final String VALUE = " and value: ? ";
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
        log.error("createTransaction");
        if (ObjectUtils.isEmpty(request.getAmount()) || ObjectUtils.isEmpty(request.getOperationTypeId()) || ObjectUtils.isEmpty(request.getAccountId())) {
            log.error("Has Data Missing Transaction");
            throw new NullPointerException();
        }
        OperationsTypeDocument typeDocument = typeService.findById(request.getOperationTypeId());
        accountService.findById(request.getAccountId());
        log.error("Save Data and Response");
        TransactionsDocument document = repository.save(mapper.requestToDocument(request));

        mqService.send(
                getQueueType(typeDocument)
                , NEW_TRANSACTION.concat(document.getTransactionsId())
                        .concat(TYPE).concat(request.getOperationTypeId())
                        .concat(VALUE).concat(request.getAmount().toString()));
        return mapper.documentToResponse(document);

    }

    private String getQueueType(OperationsTypeDocument typeDocument) {
        if (typeDocument.getDescription().contains("PAGAMENTO")) {
            return RabbitMqConstants.PAYMENT;
        }
        if (typeDocument.getDescription().contains("SAQUE")) {
            return RabbitMqConstants.WITHDRAWAL;
        }

        return RabbitMqConstants.PURCHASE;

    }

}
