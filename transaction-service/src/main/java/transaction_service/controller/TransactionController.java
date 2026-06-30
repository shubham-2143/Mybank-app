package transaction_service.controller;

import org.springframework.web.bind.annotation.*;
import transaction_service.dto.CreateTransactionRequest;
import transaction_service.dto.TransactionResponse;
import transaction_service.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public TransactionResponse create(
            @RequestBody CreateTransactionRequest request) {

        return service.create(request);
    }

    @GetMapping("/{accountNumber}")
    public List<TransactionResponse> history(
            @PathVariable String accountNumber) {

        return service.getByAccount(accountNumber);
    }

    @GetMapping("/id/{transactionId}")
    public TransactionResponse getTransaction(
            @PathVariable String transactionId) {

        return service.getByTransactionId(transactionId);
    }
}
