package account_service.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class CreateAccountResponse {

    private String accountNumber;
    private BigDecimal balance;
    private String message;
}
