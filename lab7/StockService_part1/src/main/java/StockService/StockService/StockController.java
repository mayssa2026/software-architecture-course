package StockService.StockService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {
    @GetMapping("/stock/{productNumber}")
    public int getStock(@PathVariable String productNumber) {
        return 100;
    }
}




