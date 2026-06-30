package transaction_service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private String transactionId;

    private String accountNumber;

    private String transactionType;

    private BigDecimal amount;

    private BigDecimal balanceAfterTransaction;

    private String remarks;

    private String status;

    private LocalDateTime createdAt;
}
