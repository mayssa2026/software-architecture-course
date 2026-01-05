package checkingAccount;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checking")
public class CheckingAccountController {
    private final CheckingAccountService service;

    public CheckingAccountController(CheckingAccountService service) {
        this.service = service;
    }

    @PostMapping("/accounts/{id}")
    public CheckingAccount create(@PathVariable Long id, @RequestParam double initial) {
        return service.create(id, initial);
    }

    @PostMapping("/deposit/{id}")
    public CheckingAccount deposit(@PathVariable Long id, @RequestParam double amount) {
        return service.deposit(id, amount);
    }

    @PostMapping("/withdraw/{id}")
    public CheckingAccount withdraw(@PathVariable Long id, @RequestParam double amount) {
        return service.withdraw(id, amount);
    }

    @GetMapping("/accounts/{id}")
    public CheckingAccount get(@PathVariable Long id) {
        return service.get(id);
    }
}
