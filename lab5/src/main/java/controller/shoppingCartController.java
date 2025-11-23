package controller;

import domain.ShoppingCart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ShoppingCartService;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class shoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PostMapping
    public ResponseEntity<ShoppingCart> createCart(@RequestBody ShoppingCart cart) {
        return ResponseEntity.ok(shoppingCartService.createCart(cart));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getCart(@PathVariable String id) {
        ShoppingCart cart = shoppingCartService.getCart(id);
        return cart != null ? ResponseEntity.ok(cart) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{cartId}/add/{productId}")
    public ResponseEntity<ShoppingCart> addProduct(
            @PathVariable String cartId,
            @PathVariable String productId,
            @RequestParam int qty) {
        return ResponseEntity.ok(
                shoppingCartService.addProductToCart(cartId, productId, qty)
        );
    }

    @PutMapping("/{cartId}/change/{productId}")
    public ResponseEntity<ShoppingCart> changeQty(
            @PathVariable String cartId,
            @PathVariable String productId,
            @RequestParam int qty) {

        return ResponseEntity.ok(
                shoppingCartService.changeQuantity(cartId, productId, qty)
        );
    }

    @DeleteMapping("/{cartId}/remove/{productId}")
    public ResponseEntity<ShoppingCart> removeProduct(
            @PathVariable String cartId,
            @PathVariable String productId) {

        return ResponseEntity.ok(
                shoppingCartService.removeProductFromCart(cartId, productId)
        );
    }
}
