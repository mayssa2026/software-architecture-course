package repository;

import domain.ShoppingCart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShoppingCartRepository  extends MongoRepository<ShoppingCart, String> {
    ShoppingCart findByCustomerNumber(String customerNumber);
}