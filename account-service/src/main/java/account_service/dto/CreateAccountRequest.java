package account_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateAccountRequest {

    private String customerEmail;
    private String accountType;
    private BigDecimal initialDeposit;
}
