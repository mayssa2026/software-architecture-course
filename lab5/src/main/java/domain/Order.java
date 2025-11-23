package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    String orderNumber;
    Date date;
    String status;
    List<OrderLine> orderLines = new ArrayList<>();
    CreditCard creditCard;
    ShippingOption shippingOption;
    Customer customer;
    Address shippingAddress;
    Address billingAddress;
    public void confirm() {}
}
