package account_service.controller;

import account_service.dto.CreateAccountRequest;
import account_service.dto.CreateAccountResponse;
import account_service.service.AccountService;
import org.springframework.web.bind.annotation.*;
import account_service.dto.AccountResponse;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public CreateAccountResponse createAccount(
            @RequestBody CreateAccountRequest request) {

        return service.createAccount(request);
    }
    @GetMapping("/{accountNumber}")
    public AccountResponse getAccount(
        @PathVariable String accountNumber) {

    return service.getAccount(accountNumber);
    }
    @GetMapping("/{accountNumber}/balance")
    public String getBalance(
        @PathVariable String accountNumber) {

    return service
            .getAccount(accountNumber)
            .getBalance()
            .toString();
    }
}
