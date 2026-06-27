package payment_service.controller;

import payment_service.dto.TransferRequest;
import payment_service.dto.TransferResponse;
import payment_service.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    public TransferResponse transfer(
            @RequestBody TransferRequest request) {

        return service.transfer(request);
    }
}
