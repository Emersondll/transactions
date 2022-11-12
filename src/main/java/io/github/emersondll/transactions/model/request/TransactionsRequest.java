package io.github.emersondll.transactions.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsRequest {

    private String accountId;
    private String operationTypeId;
    private BigDecimal amount;


}
