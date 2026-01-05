package service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SalaryController {

    @GetMapping("/salary")
    public Map<String, Object> salary() {
        return Map.of("salary", 120000);
    }
}


