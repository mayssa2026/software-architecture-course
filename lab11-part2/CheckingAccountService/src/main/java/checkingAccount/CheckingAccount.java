package checkingAccount;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CheckingAccount {
    @Id
    private Long id;
    private double balance;

    public CheckingAccount() {}

    public CheckingAccount(Long id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public Long getId() { return id; }
    public double getBalance() { return balance; }

    public void setId(Long id) { this.id = id; }
    public void setBalance(double balance) { this.balance = balance; }
}
