package webshop.lab5;

import domain.Address;
import domain.CartLine;
import domain.Product;
import domain.ShoppingCart;
import domain.Stock;
import domain.Supplier;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import repository.ProductRepository;
import repository.ShoppingCartRepository;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WebShopIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    private String baseUrl;

    private static final String TEST_PRODUCT_ID = "P_TEST_001";
    private static final String TEST_CART_ID = "CART_TEST_001";

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    @AfterAll
    static void cleanup(@Autowired ProductRepository productRepo,
                        @Autowired ShoppingCartRepository cartRepo) {
        // Clean up test data
        productRepo.deleteById(TEST_PRODUCT_ID);
        cartRepo.deleteById(TEST_CART_ID);
    }

    @Test
    @Order(1)
    @DisplayName("1. Add new product to the product component")
    void testAddProduct() {
        // Given
        Product product = createTestProduct();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> request = new HttpEntity<>(product, headers);

        // When
        ResponseEntity<Product> response = restTemplate.postForEntity(
            baseUrl + "/products",
            request,
            Product.class
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getProductNumber()).isEqualTo(TEST_PRODUCT_ID);
        assertThat(response.getBody().getDescription()).isEqualTo("Test Product - Wireless Speaker");
        assertThat(response.getBody().getPrice()).isEqualTo(149.99);

        System.out.println("TEST 1 PASSED: Product added successfully");
        System.out.println("  Product Number: " + response.getBody().getProductNumber());
        System.out.println("  Description: " + response.getBody().getDescription());
    }

    @Test
    @Order(2)
    @DisplayName("2. Get the product from the product component and print to console")
    void testGetProduct() {
        // When
        ResponseEntity<Product> response = restTemplate.getForEntity(
            baseUrl + "/products/" + TEST_PRODUCT_ID,
            Product.class
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getProductNumber()).isEqualTo(TEST_PRODUCT_ID);
        assertThat(response.getBody().getDescription()).isEqualTo("Test Product - Wireless Speaker");
        assertThat(response.getBody().getPrice()).isEqualTo(149.99);
        assertThat(response.getBody().getStock().getNbrInStock()).isEqualTo(100);
        assertThat(response.getBody().getSupplier().getName()).isEqualTo("TestSupplier Co");

        System.out.println("TEST 2 PASSED: Product retrieved successfully");
        printProduct(response.getBody());
    }

    @Test
    @Order(3)
    @DisplayName("3. Add this product to the shopping cart")
    void testAddProductToShoppingCart() {
        // First create a shopping cart
        ShoppingCart newCart = new ShoppingCart(TEST_CART_ID, "CUST_TEST_001", new ArrayList<>());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ShoppingCart> createRequest = new HttpEntity<>(newCart, headers);

        ResponseEntity<ShoppingCart> createResponse = restTemplate.postForEntity(
            baseUrl + "/carts",
            createRequest,
            ShoppingCart.class
        );

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createResponse.getBody()).isNotNull();

        // Now add the product to the cart
        ResponseEntity<ShoppingCart> addResponse = restTemplate.postForEntity(
            baseUrl + "/carts/" + TEST_CART_ID + "/add/" + TEST_PRODUCT_ID + "?qty=3",
            null,
            ShoppingCart.class
        );

        // Then
        assertThat(addResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(addResponse.getBody()).isNotNull();
        assertThat(addResponse.getBody().getCartLines()).hasSize(1);
        assertThat(addResponse.getBody().getCartLines().get(0).getQuantity()).isEqualTo(3);
        assertThat(addResponse.getBody().getCartLines().get(0).getProduct().getProductNumber())
            .isEqualTo(TEST_PRODUCT_ID);

        System.out.println("TEST 3 PASSED: Product added to shopping cart");
        System.out.println("  Cart ID: " + addResponse.getBody().getShoppingcartNumber());
        System.out.println("  Product added: " + TEST_PRODUCT_ID);
        System.out.println("  Quantity: 3");
    }

    @Test
    @Order(4)
    @DisplayName("4. Get the shopping cart and print to console")
    void testGetShoppingCart() {
        // When
        ResponseEntity<ShoppingCart> response = restTemplate.getForEntity(
            baseUrl + "/carts/" + TEST_CART_ID,
            ShoppingCart.class
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getShoppingcartNumber()).isEqualTo(TEST_CART_ID);
        assertThat(response.getBody().getCustomerNumber()).isEqualTo("CUST_TEST_001");
        assertThat(response.getBody().getCartLines()).hasSize(1);

        CartLine cartLine = response.getBody().getCartLines().get(0);
        assertThat(cartLine.getQuantity()).isEqualTo(3);
        assertThat(cartLine.getProduct().getProductNumber()).isEqualTo(TEST_PRODUCT_ID);

        System.out.println("TEST 4 PASSED: Shopping cart retrieved successfully");
        printShoppingCart(response.getBody());
    }

    private Product createTestProduct() {
        Address supplierAddress = new Address(
            "500 Test Street",
            "TestCity",
            "12345",
            "USA"
        );

        Supplier supplier = new Supplier(
            "SUP_TEST_001",
            "TestSupplier Co",
            "555-9999",
            "test@supplier.com",
            supplierAddress
        );

        Stock stock = new Stock(100, "WH-TEST");

        return new Product(
            TEST_PRODUCT_ID,
            "Test Product - Wireless Speaker",
            149.99,
            stock,
            new ArrayList<>(),
            supplier
        );
    }

    private void printProduct(Product product) {
        System.out.println("  Product Details:");
        System.out.println("    Product Number: " + product.getProductNumber());
        System.out.println("    Description: " + product.getDescription());
        System.out.println("    Price: $" + product.getPrice());
        System.out.println("    Stock: " + product.getStock().getNbrInStock() +
            " units at " + product.getStock().getLocationCode());
        System.out.println("    Supplier: " + product.getSupplier().getName());
    }

    private void printShoppingCart(ShoppingCart cart) {
        System.out.println("  Shopping Cart Details:");
        System.out.println("    Cart Number: " + cart.getShoppingcartNumber());
        System.out.println("    Customer: " + cart.getCustomerNumber());
        System.out.println("    Items: " + cart.getCartLines().size());

        double total = 0;
        for (CartLine line : cart.getCartLines()) {
            double lineTotal = line.getProduct().getPrice() * line.getQuantity();
            total += lineTotal;
            System.out.println("    - " + line.getProduct().getDescription() +
                " x" + line.getQuantity() + " = $" + lineTotal);
        }
        System.out.println("    TOTAL: $" + total);
    }
}