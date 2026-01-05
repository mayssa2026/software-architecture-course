package savingAccount;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/saving")
public class SavingAccountController {
    private final SavingAccountService service;

    public SavingAccountController(SavingAccountService service) {
        this.service = service;
    }

    @PostMapping("/accounts/{id}")
    public SavingAccount create(@PathVariable Long id, @RequestParam double initial) {
        return service.create(id, initial);
    }

    @PostMapping("/deposit/{id}")
    public SavingAccount deposit(@PathVariable Long id, @RequestParam double amount) {
        return service.deposit(id, amount);
    }

    @PostMapping("/withdraw/{id}")
    public SavingAccount withdraw(@PathVariable Long id, @RequestParam double amount) {
        return service.withdraw(id, amount);
    }

    @GetMapping("/accounts/{id}")
    public SavingAccount get(@PathVariable Long id) {
        return service.get(id);
    }
}
