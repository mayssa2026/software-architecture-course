package ProductService.ProductService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private final StockClient stockClient;
    public ProductController(StockClient stockClient) {
        this.stockClient = stockClient;
    }

    @GetMapping("/product/{productNumber}")
    public Product getProduct(@PathVariable String productNumber) {
        //Call StockService via Feign
        int stock = stockClient.getStock(productNumber);
        String name = "the product " + productNumber;
        return new Product(productNumber, name, stock);
    }
}
