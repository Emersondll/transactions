package io.github.emersondll.transactions.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "account")
public class AccountDocument {
    @Id
    private String accountId;
    private String documentNumber;
    private DateTime createdAt;

}
