package service;

import domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }


    public Optional<Product> getProduct(String productId) {
        return Optional.ofNullable(productRepository.findById(productId).orElse(null));
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
