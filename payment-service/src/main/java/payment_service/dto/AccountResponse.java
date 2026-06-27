package payment_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponse {

    private String accountNumber;

    private String customerEmail;

    private String accountType;

    private BigDecimal balance;
}
