package payment_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferResponse {

    private String transactionId;

    private String status;

    private String message;
}
