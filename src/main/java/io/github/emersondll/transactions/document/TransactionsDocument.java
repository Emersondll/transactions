package io.github.emersondll.transactions.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transactions")
public class TransactionsDocument {
    @Id
    private String transactionsId;
    private String accountId;
    private String operationTypeId;
    private BigDecimal amount;
    private DateTime eventDate;
}
