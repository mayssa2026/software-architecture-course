package transferclient;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class TransferService {

    private final RestTemplate restTemplate;

    public TransferService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Transactional
    public String transfer(Long checkingId, Long savingId, double amount, boolean fail) {

        restTemplate.postForObject(
                "http://localhost:8102/checking/withdraw?id=" + checkingId + "&amount=" + amount,
                null,
                String.class
        );

        if (fail) {
            throw new RuntimeException("Simulated failure");
        }

        restTemplate.postForObject(
                "http://localhost:8101/saving/deposit?id=" + savingId + "&amount=" + amount,
                null,
                String.class
        );

        return "Transfer completed";
    }
}
