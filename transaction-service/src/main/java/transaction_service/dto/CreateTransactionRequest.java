package transaction_service.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequest {

    private String transactionId;

    private String accountNumber;

    private String transactionType;

    private BigDecimal amount;

    private BigDecimal balanceAfterTransaction;

    private String remarks;

    private String status;
}
