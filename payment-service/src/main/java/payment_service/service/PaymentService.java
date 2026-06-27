package payment_service.service;

import org.springframework.stereotype.Service;
import payment_service.client.AccountServiceClient;
import payment_service.dto.DebitCreditRequest;
import payment_service.dto.TransferRequest;
import payment_service.dto.TransferResponse;
import payment_service.entity.Payment;
import payment_service.repository.PaymentRepository;
import payment_service.dto.AccountResponse;
import java.time.LocalDateTime;
import payment_service.exception.AccountNotFoundException;
import payment_service.exception.InsufficientBalanceException;
import payment_service.enums.PaymentStatus;
@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final AccountServiceClient accountClient;

    public PaymentService(PaymentRepository repository,
                          AccountServiceClient accountClient) {

        this.repository = repository;
        this.accountClient = accountClient;
    }
    public TransferResponse transfer(TransferRequest request) {

    // Validate Source Account
    AccountResponse source =
            accountClient.getAccount(request.getFromAccount());

    if (source == null) {
           throw new AccountNotFoundException("Source account not found");
    }

    // Validate Destination Account
    AccountResponse destination =
            accountClient.getAccount(request.getToAccount());

    if (destination == null) {
            throw new AccountNotFoundException("Destination account not found");
    }

    // Check Balance
    if (source.getBalance().compareTo(request.getAmount()) < 0) {
           throw new InsufficientBalanceException(
        "Account balance is insufficient for this transfer");
    }

    // Debit Source
    accountClient.debit(
            new DebitCreditRequest(
                    request.getFromAccount(),
                    request.getAmount()));

    // Credit Destination
    accountClient.credit(
            new DebitCreditRequest(
                    request.getToAccount(),
                    request.getAmount()));

    // Save Payment
    String transactionId = "TXN" + System.currentTimeMillis();

    Payment payment = Payment.builder()
            .transactionId(transactionId)
            .fromAccount(request.getFromAccount())
            .toAccount(request.getToAccount())
            .amount(request.getAmount())
            .status(PaymentStatus.SUCCESS)
            .createdAt(LocalDateTime.now())
            .build();

    repository.save(payment);

    return TransferResponse.builder()
        .transactionId(transactionId)
        .status(PaymentStatus.SUCCESS.name())
        .message("Money transferred successfully")
        .build();
}
}
