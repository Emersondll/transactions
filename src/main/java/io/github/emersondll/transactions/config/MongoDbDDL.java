package io.github.emersondll.transactions.config;

import io.github.emersondll.transactions.config.constants.MongoDbOperationTypeConstants;
import io.github.emersondll.transactions.document.OperationsTypeDocument;
import io.github.emersondll.transactions.repository.OperationsTypeRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoDbDDL {

    @Autowired
    private OperationsTypeRepository operationsTypeRepository;

    @PostConstruct
    private void init() {
        operationsTypeRepository.saveAll(operationsTypeDocument());
    }

    private List<OperationsTypeDocument> operationsTypeDocument() {
        List<OperationsTypeDocument> documentList = new ArrayList<>();
        documentList.add(OperationsTypeDocument.builder().operationsId("1").description(MongoDbOperationTypeConstants.BUY_AT_CASH).createdAt(DateTime.now()).build());
        documentList.add(OperationsTypeDocument.builder().operationsId("2").description(MongoDbOperationTypeConstants.INSTALLMENT_PURCHASE).createdAt(DateTime.now()).build());
        documentList.add(OperationsTypeDocument.builder().operationsId("3").description(MongoDbOperationTypeConstants.WITHDRAWAL).createdAt(DateTime.now()).build());
        documentList.add(OperationsTypeDocument.builder().operationsId("4").description(MongoDbOperationTypeConstants.PAYMENT).createdAt(DateTime.now()).build());
        return documentList;

    }
}
