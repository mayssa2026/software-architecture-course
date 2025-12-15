package products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import products.config.RestTemplateConfig;

@Service
public class ProductService {
    @Autowired
    private RestTemplate restTemplate;
    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public int getStock(String productNumber) {
        return restTemplate.getForObject(
                "http://stock-service/stock/" + productNumber,
                Integer.class
        );
    }
    public Product getProduct(String productNumber) {
        int stock = getStock(productNumber);
        Product product = new Product("IPhone 11", productNumber);
        product.setNumberOnStock(stock);
        return product;
    }
}