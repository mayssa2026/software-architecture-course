package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "shoppingcarts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {
    @Id
    private String shoppingcartNumber; // UML name matches ID

    private String customerNumber; // because Customer → ShoppingCart (1 to many)

    private List<CartLine> cartLines = new ArrayList<>();


    public void addToCart(Product product, int quantity) {
        cartLines.add(new CartLine(quantity, product));
    }

    public void removeFromCart(String productNumber) {
        cartLines.removeIf(cl -> cl.getProduct().getProductNumber().equals(productNumber));
    }

    public void changeQuantity(String productNumber, int newQty) {
        cartLines.stream()
                .filter(cl -> cl.getProduct().getProductNumber().equals(productNumber))
                .findFirst()
                .ifPresent(cl -> cl.setQuantity(newQty));
    }

    public void checkout() {}
}
