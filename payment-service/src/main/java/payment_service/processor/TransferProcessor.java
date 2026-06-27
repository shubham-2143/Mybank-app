package payment_service.processor;

import org.springframework.stereotype.Component;
import payment_service.client.AccountServiceClient;
import payment_service.dto.DebitCreditRequest;
import payment_service.dto.TransferRequest;
import payment_service.dto.TransferResponse;
import payment_service.entity.Payment;
import payment_service.enums.PaymentStatus;
import payment_service.repository.PaymentRepository;

import java.time.LocalDateTime;

@Component
public class TransferProcessor {

    private final AccountServiceClient accountClient;
    private final PaymentRepository paymentRepository;

    public TransferProcessor(AccountServiceClient accountClient,
                             PaymentRepository paymentRepository) {

        this.accountClient = accountClient;
        this.paymentRepository = paymentRepository;
    }

    public TransferResponse process(TransferRequest request) {

        String transactionId = "TXN" + System.currentTimeMillis();

        Payment payment = Payment.builder()
                .transactionId(transactionId)
                .fromAccount(request.getFromAccount())
                .toAccount(request.getToAccount())
                .amount(request.getAmount())
                .status(PaymentStatus.STARTED)
                .createdAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        try {

            payment.setStatus(PaymentStatus.VALIDATED);
            paymentRepository.save(payment);

            accountClient.debit(
                    new DebitCreditRequest(
                            request.getFromAccount(),
                            request.getAmount()));

            payment.setStatus(PaymentStatus.DEBIT_COMPLETED);
            paymentRepository.save(payment);

            accountClient.credit(
                    new DebitCreditRequest(
                            request.getToAccount(),
                            request.getAmount()));

            payment.setStatus(PaymentStatus.CREDIT_COMPLETED);
            paymentRepository.save(payment);

            payment.setStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);

            return TransferResponse.builder()
                    .transactionId(transactionId)
                    .status(PaymentStatus.SUCCESS.name())
                    .message("Money transferred successfully")
                    .build();

        } catch (Exception ex) {

            rollback(request);

            payment.setStatus(PaymentStatus.ROLLED_BACK);
            paymentRepository.save(payment);

            throw new RuntimeException(
                    "Transfer failed. Amount rolled back.");
        }
    }

    private void rollback(TransferRequest request) {

        accountClient.credit(
                new DebitCreditRequest(
                        request.getFromAccount(),
                        request.getAmount()));
    }
}
