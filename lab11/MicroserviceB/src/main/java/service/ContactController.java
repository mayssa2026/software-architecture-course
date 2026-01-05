package service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ContactController {

    @GetMapping("/contact")
    public Map<String, String> contact() {
        return Map.of("phone", "+1-312-555-1212");
    }
}
