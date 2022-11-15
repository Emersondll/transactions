package io.github.emersondll.transactions.service.impl;

import io.github.emersondll.transactions.document.OperationsTypeDocument;
import io.github.emersondll.transactions.document.TransactionsDocument;
import io.github.emersondll.transactions.mapper.TransactionMapper;
import io.github.emersondll.transactions.model.request.TransactionsRequest;
import io.github.emersondll.transactions.model.response.AccountResponse;
import io.github.emersondll.transactions.model.response.TransactionsResponse;
import io.github.emersondll.transactions.repository.TransactionsRepository;
import io.github.emersondll.transactions.service.AccountService;
import io.github.emersondll.transactions.service.OperationsTypeService;
import io.github.emersondll.transactions.service.RabbitMqService;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("TransactionsServiceImpl Test")
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    private TransactionsServiceImpl testClass;
    @Mock
    private AccountService accountService;

    @Mock
    private OperationsTypeService typeService;
    @Mock
    private TransactionsRepository repository;
    @Mock
    private TransactionMapper mapper;
    @Mock
    private RabbitMqService mqService;

    @BeforeEach
    public void setup() {
        testClass = new TransactionsServiceImpl(accountService, typeService, repository, mapper, mqService);
    }

    @Test
    @DisplayName("Validate getQueueType PURCHASE 1")
    void getQueueTypePurchaseCase1() {

        String response = testClass.getQueueType(OperationsTypeDocument.builder().description("COMPRA A VISTA").build());
        Assertions.assertEquals("PURCHASE", response);
    }

    @Test
    @DisplayName("Validate getQueueType PURCHASE 2")
    void getQueueTypePurchaseCase2() {

        String response = testClass.getQueueType(OperationsTypeDocument.builder().description("COMPRA PARCELADA").build());
        Assertions.assertEquals("PURCHASE", response);
    }

    @Test
    @DisplayName("Validate getQueueType WITHDRAWL")
    void getQueueTypeWithdrawl() {

        String response = testClass.getQueueType(OperationsTypeDocument.builder().description("SAQUE").build());
        Assertions.assertEquals("WITHDRAWAL", response);
    }

    @Test
    @DisplayName("Validate getQueueType PAYMENT")
    void getQueueTypePayment() {

        String response = testClass.getQueueType(OperationsTypeDocument.builder().description("PAGAMENTO").build());
        Assertions.assertEquals("PAYMENT", response);
    }

    @Test
    @DisplayName("Validate correct signal in operations Purchase 2")
    void validateSignalValuesPurchase1() {

        TransactionsRequest request = testClass.validateSignalValues(
                getTransactionsRequestOperation(), getOperationsTypeDocument());
        Assertions.assertEquals(new BigDecimal(-10), request.getAmount());
    }

    @Test
    @DisplayName("Validate correct signal in operations Purchase 1.1")
    void validateSignalValuesPurchase1AnotherSignalIn() {

        TransactionsRequest transactionsRequest = getTransactionsRequestOperation();
        transactionsRequest.setAmount(new BigDecimal(-10));

        TransactionsRequest request = testClass.validateSignalValues(transactionsRequest, getOperationsTypeDocument());
        Assertions.assertEquals(new BigDecimal(-10), request.getAmount());
    }

    @Test
    @DisplayName("Validate correct signal in operations Purchase 2")
    void validateSignalValuesPurchase2() {
        OperationsTypeDocument typeDocument = getOperationsTypeDocument();
        typeDocument.setDescription("COMPRA PARCELADA");
        TransactionsRequest request = testClass.validateSignalValues(
                getTransactionsRequestOperation(), typeDocument);
        Assertions.assertEquals(new BigDecimal(-10), request.getAmount());
    }

    @Test
    @DisplayName("Validate correct signal in operations Purchase 2.1")
    void validateSignalValuesPurchase2AnotherSignalIn() {
        OperationsTypeDocument typeDocument = getOperationsTypeDocument();
        typeDocument.setDescription("SAQUE");
        TransactionsRequest transactionsRequest = getTransactionsRequestOperation();
        transactionsRequest.setAmount(new BigDecimal(-10));

        TransactionsRequest request = testClass.validateSignalValues(transactionsRequest, typeDocument);
        Assertions.assertEquals(new BigDecimal(-10), request.getAmount());
    }

    @Test
    @DisplayName("Validate correct signal in operations Purchase 3")
    void validateSignalValuesPurchase3() {
        OperationsTypeDocument typeDocument = getOperationsTypeDocument();
        typeDocument.setDescription("PAGAMENTO");
        TransactionsRequest request = testClass.validateSignalValues(
                getTransactionsRequestOperation(), typeDocument);
        Assertions.assertEquals(new BigDecimal(10), request.getAmount());
    }

    @Test
    @DisplayName("Validate correct signal in operations Purchase 3.1")
    void validateSignalValuesPurchase3AnotherSignalIn() {
        OperationsTypeDocument typeDocument = getOperationsTypeDocument();
        typeDocument.setDescription("PAGAMENTO");

        TransactionsRequest transactionsRequest = getTransactionsRequestOperation();
        transactionsRequest.setAmount(new BigDecimal(-10));

        TransactionsRequest request = testClass.validateSignalValues(transactionsRequest, typeDocument);
        Assertions.assertEquals(new BigDecimal(10), request.getAmount());
    }

    @Test
    @DisplayName("Create Account With Success Without DB Register")
    void createTransaction() throws Exception {
        Mockito.when(typeService.findById(Mockito.anyString())).thenReturn(getOperationsTypeDocument());
        Mockito.when(accountService.findById(Mockito.anyString())).thenReturn(getAccountResponse());
        Mockito.when(mapper.requestToDocument(Mockito.any(TransactionsRequest.class))).thenReturn(getTransactionsDocument());
        Mockito.when(repository.save(Mockito.any(TransactionsDocument.class))).thenReturn(getTransactionsDocument());
        Mockito.when(mapper.documentToResponse(Mockito.any(TransactionsDocument.class))).thenReturn(getTransactionResponse());

        TransactionsResponse response = testClass.createTransaction(getTransactionsRequestOperation());
        Assertions.assertEquals(new BigDecimal(20), response.getAmount());

    }

    @Test
    @DisplayName("Exception when create Account With")
    void createTransactionWithException() throws Exception {

        TransactionsRequest request = getTransactionsRequestOperation();
        request.setAmount(null);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            testClass.createTransaction(request);
        });

        String expectedMessage = "Error";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    /****Helper****/
    private TransactionsResponse getTransactionResponse() {
        return TransactionsResponse.builder()
                .accountId("1")
                .amount(new BigDecimal(20))
                .transactionsId("5")
                .eventDate(DateTime.now())
                .operationTypeId("6")
                .build();
    }

    private TransactionsDocument getTransactionsDocument() {
        return TransactionsDocument.builder()
                .transactionsId("1")
                .amount(new BigDecimal(10)).build();
    }

    private TransactionsRequest getTransactionsRequestOperation() {
        return TransactionsRequest.builder()
                .accountId("accountId")
                .amount(new BigDecimal(10))
                .operationTypeId("1")
                .build();
    }

    private OperationsTypeDocument getOperationsTypeDocument() {
        return OperationsTypeDocument.builder()
                .operationsId("1")
                .description("COMPRA A VISTA")
                .build();
    }

    private AccountResponse getAccountResponse() {
        return AccountResponse.builder()
                .accountId("456").build();

    }

}
