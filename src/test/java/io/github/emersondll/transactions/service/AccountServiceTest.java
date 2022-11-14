package io.github.emersondll.transactions.service;

import io.github.emersondll.transactions.document.AccountDocument;
import io.github.emersondll.transactions.mapper.AccountMapper;
import io.github.emersondll.transactions.model.request.AccountRequest;
import io.github.emersondll.transactions.model.response.AccountResponse;
import io.github.emersondll.transactions.repository.AccountRepository;
import io.github.emersondll.transactions.service.impl.AccountServiceImpl;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLDataException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("SacolaServiceImpl Test")
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    private AccountServiceImpl testClass;
    @Mock
    private AccountRepository repository;
    @Mock
    private AccountMapper mapper;
    @Mock
    private RabbitMqService mqService;

    @BeforeEach
    public void setup() {
        testClass = new AccountServiceImpl(repository, mapper, mqService);
    }

    @Test
    @DisplayName("Create Account With Success with DB Register")
    void createAccountOldRegister() {
        Mockito.when(repository.findByDocumentNumber(Mockito.anyString())).thenReturn(getAccountDocument());
        Mockito.when(mapper.convertDocumentToResponse(Mockito.any(AccountDocument.class))).thenReturn(getAccountResponse());

        AccountResponse response = testClass.createAccount(getAccountRequest());
        Assertions.assertEquals("456", response.getAccountId());
    }

    @Test
    @DisplayName("Create Account With Success Without DB Register")
    void createAccountNewRegister() {
        Mockito.when(repository.findByDocumentNumber(Mockito.anyString())).thenReturn(null);
        Mockito.when(mapper.convertRequestToDocument(Mockito.any(AccountRequest.class))).thenReturn(getAccountDocument());
        Mockito.when(repository.save(Mockito.any(AccountDocument.class))).thenReturn(getAccountDocument());
        Mockito.when(mapper.convertDocumentToResponse(Mockito.any(AccountDocument.class))).thenReturn(getAccountResponse());

        AccountResponse response = testClass.createAccount(getAccountRequest());
        Assertions.assertEquals("456", response.getAccountId());
    }

    @Test
    @DisplayName("findById  With Success With DB Register")
    void findById() throws Exception {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(getAccountDocument()));
        Mockito.when(mapper.convertDocumentToResponseComplete(Mockito.any(AccountDocument.class))).thenReturn(getAccountResponse());

        AccountResponse response = testClass.findById("AccountID");
        Assertions.assertEquals("456", response.getAccountId());
    }

    @Test
    @DisplayName("findById  Without data DB Register")
    void findByIdWithEmpty() throws Exception {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        Exception exception = assertThrows(SQLDataException.class, () -> {
            testClass.findById("AccountID");
        });

        String expectedMessage = "Id Not Found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /****Helper****/

    private AccountRequest getAccountRequest() {
        return AccountRequest.builder()
                .documentNumber("963698").build();
    }

    private AccountDocument getAccountDocument() {
        return AccountDocument.builder()
                .accountId("123")
                .accountId("456")
                .createdAt(DateTime.now()).build();
    }

    private AccountResponse getAccountResponse() {
        return AccountResponse.builder()
                .accountId("123")
                .accountId("456").build();

    }

}
