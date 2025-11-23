package service;

import domain.CartLine;
import domain.Product;
import domain.ShoppingCart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.ProductRepository;
import repository.ShoppingCartRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    public ShoppingCart getCart(String cartId) {
        return shoppingCartRepository.findById(cartId).orElse(null);
    }

    public ShoppingCart createCart(ShoppingCart cart) {
        return shoppingCartRepository.save(cart);
    }

    public ShoppingCart addProductToCart(String cartId, String productNumber, int quantity) {
        ShoppingCart cart = getCart(cartId);

        Product product = productRepository.findById(productNumber)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        cart.getCartLines().add(new CartLine(quantity, product));

        return shoppingCartRepository.save(cart);
    }

    public ShoppingCart removeProductFromCart(String cartId, String productNumber) {
        ShoppingCart cart = getCart(cartId);

        cart.getCartLines()
                .removeIf(cl -> cl.getProduct().getProductNumber().equals(productNumber));

        return shoppingCartRepository.save(cart);
    }

    public ShoppingCart changeQuantity(String cartId, String productNumber, int qty) {
        ShoppingCart cart = getCart(cartId);

        cart.getCartLines().stream()
                .filter(cl -> cl.getProduct().getProductNumber().equals(productNumber))
                .findFirst()
                .ifPresent(cl -> cl.setQuantity(qty));

        return shoppingCartRepository.save(cart);
    }
}


