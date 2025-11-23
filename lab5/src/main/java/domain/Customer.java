package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    String customerNumber;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    Address address;
    Account account;
    List<Order> orders = new ArrayList<>();
    List<CreditCard> creditCards;

}
