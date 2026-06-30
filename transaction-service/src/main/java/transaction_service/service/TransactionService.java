package transaction_service.service;

import org.springframework.stereotype.Service;
import transaction_service.dto.CreateTransactionRequest;
import transaction_service.dto.TransactionResponse;
import transaction_service.entity.Transaction;
import transaction_service.exception.TransactionNotFoundException;
import transaction_service.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public TransactionResponse create(CreateTransactionRequest request) {

        Transaction transaction = Transaction.builder()
                .transactionId(request.getTransactionId())
                .accountNumber(request.getAccountNumber())
                .transactionType(request.getTransactionType())
                .amount(request.getAmount())
                .balanceAfterTransaction(request.getBalanceAfterTransaction())
                .remarks(request.getRemarks())
                .status(request.getStatus())
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(transaction);

        return map(transaction);
    }

    public List<TransactionResponse> getByAccount(String accountNumber) {

        return repository.findByAccountNumber(accountNumber)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public TransactionResponse getByTransactionId(String transactionId) {

        Transaction transaction = repository
                .findByTransactionId(transactionId)
                .orElseThrow(() ->
                        new TransactionNotFoundException(
                                "Transaction not found"));

        return map(transaction);
    }

    private TransactionResponse map(Transaction transaction) {

        return TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .accountNumber(transaction.getAccountNumber())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .balanceAfterTransaction(
                        transaction.getBalanceAfterTransaction())
                .remarks(transaction.getRemarks())
                .status(transaction.getStatus())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
