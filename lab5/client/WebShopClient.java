package client;

import domain.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class WebShopClient {

    private static final String BASE_URL = "http://localhost:9090";
    private final RestTemplate restTemplate;

    public WebShopClient() {
        this.restTemplate = new RestTemplate();
    }

    public static void main(String[] args) {
        WebShopClient client = new WebShopClient();

        // 1. Add new product to the product component
        System.out.println("=== 1. Adding new product ===");
        Product newProduct = client.addProduct();
        System.out.println("Product added successfully!");
        System.out.println();

        // 2. Get the product from the product component and print to console
        System.out.println("=== 2. Getting product from server ===");
        Product retrievedProduct = client.getProduct(newProduct.getProductNumber());
        client.printProduct(retrievedProduct);
        System.out.println();

        // 3. Add this product to the shopping cart
        System.out.println("=== 3. Adding product to shopping cart ===");
        ShoppingCart cart = client.createCartAndAddProduct(retrievedProduct);
        System.out.println("Product added to shopping cart!");
        System.out.println();

        // 4. Get the shopping cart and print to console
        System.out.println("=== 4. Getting shopping cart ===");
        ShoppingCart retrievedCart = client.getShoppingCart(cart.getShoppingcartNumber());
        client.printShoppingCart(retrievedCart);
    }

    private Product addProduct() {
        // Create a new product with all required fields
        Address supplierAddress = new Address(
            "100 Innovation Drive",
            "Boston",
            "02101",
            "USA"
        );

        Supplier supplier = new Supplier(
            "SUP100",
            "ElectroTech Supplies",
            "555-1234",
            "contact@electrotech.com",
            supplierAddress
        );

        Stock stock = new Stock(50, "WH-D4");

        Product product = new Product(
            "P100",
            "Smart Home Hub Controller",
            299.99,
            stock,
            new ArrayList<>(),
            supplier
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Product> request = new HttpEntity<>(product, headers);

        ResponseEntity<Product> response = restTemplate.postForEntity(
            BASE_URL + "/products",
            request,
            Product.class
        );

        return response.getBody();
    }

    private Product getProduct(String productId) {
        ResponseEntity<Product> response = restTemplate.getForEntity(
            BASE_URL + "/products/" + productId,
            Product.class
        );
        return response.getBody();
    }

    private ShoppingCart createCartAndAddProduct(Product product) {
        // First create a shopping cart
        ShoppingCart newCart = new ShoppingCart(
            "CART100",
            "CUST001",
            new ArrayList<>()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ShoppingCart> createRequest = new HttpEntity<>(newCart, headers);

        ResponseEntity<ShoppingCart> createResponse = restTemplate.postForEntity(
            BASE_URL + "/carts",
            createRequest,
            ShoppingCart.class
        );

        ShoppingCart createdCart = createResponse.getBody();

        // Now add the product to the cart
        ResponseEntity<ShoppingCart> addResponse = restTemplate.postForEntity(
            BASE_URL + "/carts/" + createdCart.getShoppingcartNumber() +
                "/add/" + product.getProductNumber() + "?qty=2",
            null,
            ShoppingCart.class
        );

        return addResponse.getBody();
    }

    private ShoppingCart getShoppingCart(String cartId) {
        ResponseEntity<ShoppingCart> response = restTemplate.getForEntity(
            BASE_URL + "/carts/" + cartId,
            ShoppingCart.class
        );
        return response.getBody();
    }

    private void printProduct(Product product) {
        System.out.println("Product Details:");
        System.out.println("  Product Number: " + product.getProductNumber());
        System.out.println("  Description: " + product.getDescription());
        System.out.println("  Price: $" + product.getPrice());
        System.out.println("  Stock:");
        System.out.println("    Quantity: " + product.getStock().getNbrInStock());
        System.out.println("    Location: " + product.getStock().getLocationCode());
        System.out.println("  Supplier:");
        System.out.println("    Name: " + product.getSupplier().getName());
        System.out.println("    Email: " + product.getSupplier().getEmail());
        System.out.println("    Phone: " + product.getSupplier().getPhone());
        if (product.getSupplier().getAddress() != null) {
            Address addr = product.getSupplier().getAddress();
            System.out.println("    Address: " + addr.getStreet() + ", " +
                addr.getCity() + ", " + addr.getZip() + ", " + addr.getCountry());
        }
        System.out.println("  Reviews: " + product.getReviews().size());
    }

    private void printShoppingCart(ShoppingCart cart) {
        System.out.println("Shopping Cart Details:");
        System.out.println("  Cart Number: " + cart.getShoppingcartNumber());
        System.out.println("  Customer Number: " + cart.getCustomerNumber());
        System.out.println("  Items in Cart: " + cart.getCartLines().size());

        double total = 0;
        for (CartLine line : cart.getCartLines()) {
            Product p = line.getProduct();
            double lineTotal = p.getPrice() * line.getQuantity();
            total += lineTotal;

            System.out.println("  ---");
            System.out.println("    Product: " + p.getDescription());
            System.out.println("    Product Number: " + p.getProductNumber());
            System.out.println("    Unit Price: $" + p.getPrice());
            System.out.println("    Quantity: " + line.getQuantity());
            System.out.println("    Line Total: $" + lineTotal);
        }
        System.out.println("  ---");
        System.out.println("  CART TOTAL: $" + total);
    }
}