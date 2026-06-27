package payment_service.service;

import org.springframework.stereotype.Service;
import payment_service.dto.TransferRequest;
import payment_service.dto.TransferResponse;
import payment_service.processor.TransferProcessor;
import payment_service.validator.TransferValidator;

@Service
public class PaymentService {

    private final TransferValidator validator;
    private final TransferProcessor processor;

    public PaymentService(
            TransferValidator validator,
            TransferProcessor processor) {

        this.validator = validator;
        this.processor = processor;
    }

    public TransferResponse transfer(
            TransferRequest request) {

        validator.validate(request);

        return processor.process(request);
    }
}
