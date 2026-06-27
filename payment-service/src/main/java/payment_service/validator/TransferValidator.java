package payment_service.validator;

import org.springframework.stereotype.Component;
import payment_service.client.AccountServiceClient;
import payment_service.dto.AccountResponse;
import payment_service.dto.TransferRequest;
import payment_service.exception.AccountNotFoundException;
import payment_service.exception.InsufficientBalanceException;

@Component
public class TransferValidator {

    private final AccountServiceClient accountClient;

    public TransferValidator(AccountServiceClient accountClient) {
        this.accountClient = accountClient;
    }

    public void validate(TransferRequest request) {

        AccountResponse source =
                accountClient.getAccount(request.getFromAccount());

        if (source == null) {
            throw new AccountNotFoundException("Source account not found");
        }

        AccountResponse destination =
                accountClient.getAccount(request.getToAccount());

        if (destination == null) {
            throw new AccountNotFoundException("Destination account not found");
        }

        if (source.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException(
                    "Account balance is insufficient for this transfer");
        }
    }
}
