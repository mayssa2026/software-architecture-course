package service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import java.util.List;


import java.util.Map;

@RestController
public class CompanyController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/productdata")
    public List<String> productData() {
        return List.of("Laptop", "Mouse", "Keyboard");
    }

    @GetMapping("/employeedata")
    public Object employeeData(@RequestHeader("Authorization") String authHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader); // forward same Bearer token to B
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                "http://localhost:8091/contact",
                HttpMethod.GET,
                entity,
                Object.class
        ).getBody();
    }

    @GetMapping("/salarydata")
    public Object salaryData(@RequestHeader("Authorization") String authHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader); // forward token to C
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                "http://localhost:8092/salary",
                HttpMethod.GET,
                entity,
                Object.class
        ).getBody();
    }
}
