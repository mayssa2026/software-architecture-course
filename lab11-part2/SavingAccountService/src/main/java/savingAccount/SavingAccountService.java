package savingAccount;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SavingAccountService {
    private final SavingAccountRepository repo;

    public SavingAccountService(SavingAccountRepository repo) {
        this.repo = repo;
    }

    public SavingAccount create(Long id, double initial) {
        return repo.save(new SavingAccount(id, initial));
    }

    @Transactional
    public SavingAccount deposit(Long id, double amount) {
        SavingAccount acc = repo.findById(id).orElseThrow();
        acc.setBalance(acc.getBalance() + amount);
        return acc;
    }

    @Transactional
    public SavingAccount withdraw(Long id, double amount) {
        SavingAccount acc = repo.findById(id).orElseThrow();
        if (acc.getBalance() < amount) throw new IllegalArgumentException("Insufficient funds");
        acc.setBalance(acc.getBalance() - amount);
        return acc;
    }

    public SavingAccount get(Long id) {
        return repo.findById(id).orElseThrow();
    }
}
