package account_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DebitCreditRequest {

    private String accountNumber;
    private BigDecimal amount;
}
