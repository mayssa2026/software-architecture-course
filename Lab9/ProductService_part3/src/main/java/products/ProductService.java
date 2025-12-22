package products;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Service
public class ProductService {
	@Autowired
	StockFeignClient stockClient;

    @CircuitBreaker(name = "stockservice", fallbackMethod = "stockFallback")
    public Product getProduct(String productNumber) {
        int stock = stockClient.getStock(productNumber);
        Product product = new Product("IPhone 11", productNumber);
        product.setNumberOnStock(stock);
        return product;
    }

    public Product stockFallback(String productNumber, Throwable t) {
        Product product = new Product("IPhone 11", productNumber);
        product.setNumberOnStock(0); // default value
        return product;
    }

    @FeignClient(name = "stockservice")
    interface StockFeignClient {
		@RequestMapping("/stock/{productNumber}")
		public Integer getStock(@PathVariable("productNumber") String productNumber);
	}

}
