package com.example.lab10.services;

import com.example.lab10.model.ProductCommand;
import com.example.lab10.model.ProductQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.lab10.repositories.ProductCommandRepository;
import com.example.lab10.repositories.ProductQueryRepository;
import com.example.lab10.repositories.StockRepository;

@Service
public class ProductCommandService {
    private final ProductCommandRepository productRepo;
    private final StockRepository stockRepo;
    private final ProductQueryRepository queryRepo;

    public ProductCommandService(ProductCommandRepository productRepo,
                                 StockRepository stockRepo,
                                 ProductQueryRepository queryRepo) {
        this.productRepo = productRepo;
        this.stockRepo = stockRepo;
        this.queryRepo = queryRepo;
    }

    @Transactional
    public ProductCommand createOrUpdate(String productNumber, String name, double price) {
        ProductCommand product = productRepo
                .findByProductNumber(productNumber)
                .orElse(new ProductCommand(productNumber, name, price));

        product.setName(name);
        product.setPrice(price);
        ProductCommand saved = productRepo.save(product);

        // update read model
        int qty = stockRepo.findByProductNumber(productNumber)
                .map(s -> s.getQuantity())
                .orElse(0);

        ProductQuery pq = queryRepo.findByProductNumber(productNumber)
                .orElse(new ProductQuery(productNumber, name, price, qty));
        pq.setName(name);
        pq.setPrice(price);
        pq.setNumberInStock(qty);
        queryRepo.save(pq);

        return saved;
    }

    @Transactional
    public void delete(String productNumber) {
        productRepo.deleteByProductNumber(productNumber);
        stockRepo.deleteByProductNumber(productNumber);
        queryRepo.deleteByProductNumber(productNumber);
    }
}
