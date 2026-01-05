package checkingAccount;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckingAccountService {
    private final CheckingAccountRepository repo;

    public CheckingAccountService(CheckingAccountRepository repo) {
        this.repo = repo;
    }

    public CheckingAccount create(Long id, double initial) {
        return repo.save(new CheckingAccount(id, initial));
    }

    @Transactional
    public CheckingAccount deposit(Long id, double amount) {
        CheckingAccount acc = repo.findById(id).orElseThrow();
        acc.setBalance(acc.getBalance() + amount);
        return acc;
    }

    @Transactional
    public CheckingAccount withdraw(Long id, double amount) {
        CheckingAccount acc = repo.findById(id).orElseThrow();
        if (acc.getBalance() < amount) throw new IllegalArgumentException("Insufficient funds");
        acc.setBalance(acc.getBalance() - amount);
        return acc;
    }

    public CheckingAccount get(Long id) {
        return repo.findById(id).orElseThrow();
    }
}
