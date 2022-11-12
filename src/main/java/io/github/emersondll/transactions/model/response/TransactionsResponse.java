package io.github.emersondll.transactions.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsResponse {

    private String transactionsId;
    private String accountId;
    private String operationTypeId;
    private BigDecimal amount;
    private DateTime eventDate;
}
