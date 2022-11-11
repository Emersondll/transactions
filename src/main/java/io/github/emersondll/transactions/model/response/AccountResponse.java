package io.github.emersondll.transactions.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {
    private String accountId;
}
