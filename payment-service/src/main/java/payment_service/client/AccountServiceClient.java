package payment_service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import payment_service.dto.DebitCreditRequest;
import payment_service.dto.AccountResponse;

@Component
public class AccountServiceClient {

    private final RestTemplate restTemplate;

    private static final String ACCOUNT_SERVICE =
            "http://localhost:8082/api/accounts";

    public AccountServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean accountExists(String accountNumber) {

        Boolean response = restTemplate.getForObject(
                ACCOUNT_SERVICE + "/exists/" + accountNumber,
                Boolean.class);

        return Boolean.TRUE.equals(response);
    }

    public AccountResponse getAccount(String accountNumber) {

    return restTemplate.getForObject(
            ACCOUNT_SERVICE + "/" + accountNumber,
            AccountResponse.class);
    }

    public void debit(DebitCreditRequest request) {

        restTemplate.put(
                ACCOUNT_SERVICE + "/debit",
                request);
    }

    public void credit(DebitCreditRequest request) {

        restTemplate.put(
                ACCOUNT_SERVICE + "/credit",
                request);
    }
}
