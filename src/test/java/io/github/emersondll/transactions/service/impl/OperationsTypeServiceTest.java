package io.github.emersondll.transactions.service.impl;

import io.github.emersondll.transactions.document.OperationsTypeDocument;
import io.github.emersondll.transactions.repository.OperationsTypeRepository;
import io.github.emersondll.transactions.service.OperationsTypeService;
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

@DisplayName("OperationsTypeServiceTest Test")
@ExtendWith(MockitoExtension.class)
class OperationsTypeServiceTest {

    private OperationsTypeService testClass;
    @Mock
    private OperationsTypeRepository repository;


    @BeforeEach
    public void setup() {
        testClass = new OperationsTypeServiceImpl(repository);
    }


    @Test
    @DisplayName("findById  With Success With DB Register")
    void findById() throws Exception {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(getOperationsTypeDocument()));

        OperationsTypeDocument response = testClass.findById("AccountID");
        Assertions.assertEquals("456", response.getOperationsId());
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


    private OperationsTypeDocument getOperationsTypeDocument() {
        return OperationsTypeDocument.builder()
                .operationsId("456")
                .description("Description test")
                .createdAt(DateTime.now()).build();
    }


}
