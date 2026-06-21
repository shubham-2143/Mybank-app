package account_service.service;

import account_service.dto.CreateAccountRequest;
import account_service.dto.CreateAccountResponse;
import account_service.entity.Account;
import account_service.repository.AccountRepository;
import org.springframework.stereotype.Service;
import account_service.dto.AccountResponse;
import java.time.LocalDateTime;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public CreateAccountResponse createAccount(
            CreateAccountRequest request) {

        String accountNumber =
                String.valueOf(System.currentTimeMillis());

        Account account = Account.builder()
                .accountNumber(accountNumber)
                .customerEmail(request.getCustomerEmail())
                .accountType(request.getAccountType())
                .balance(request.getInitialDeposit())
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(account);

        return CreateAccountResponse.builder()
                .accountNumber(accountNumber)
                .balance(account.getBalance())
                .message("Account Created Successfully")
                .build();
    }

    public AccountResponse getAccount(String accountNumber) {

    Account account = repository
            .findByAccountNumber(accountNumber)
            .orElseThrow(() ->
                    new RuntimeException("Account not found"));

    return AccountResponse.builder()
            .accountNumber(account.getAccountNumber())
            .customerEmail(account.getCustomerEmail())
            .accountType(account.getAccountType())
            .balance(account.getBalance())
            .build();
   } 
}
