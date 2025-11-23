package repository;

import domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByDescription(String description);
}
