package transaction_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import transaction_service.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountNumber(String accountNumber);

    Optional<Transaction> findByTransactionId(String transactionId);

}
