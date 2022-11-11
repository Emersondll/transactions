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
@Document(collection = "operationsType")
public class OperationsTypeDocument {
    @Id
    private String operationsId;
    private String description;
    private DateTime createdAt;
}
