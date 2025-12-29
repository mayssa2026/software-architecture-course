package com.example.lab10.services;

import com.example.lab10.model.ProductQuery;
import com.example.lab10.model.Stock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.lab10.repositories.ProductCommandRepository;
import com.example.lab10.repositories.ProductQueryRepository;
import com.example.lab10.repositories.StockRepository;

@Service
public class StockCommandService {
    private final StockRepository stockRepo;
    private final ProductCommandRepository productRepo;
    private final ProductQueryRepository queryRepo;

    public StockCommandService(StockRepository stockRepo,
                               ProductCommandRepository productRepo,
                               ProductQueryRepository queryRepo) {
        this.stockRepo = stockRepo;
        this.productRepo = productRepo;
        this.queryRepo = queryRepo;
    }

    @Transactional
    public Stock createOrUpdate(String productNumber, int quantity) {
        Stock stock = stockRepo.findByProductNumber(productNumber)
                .orElse(new Stock(productNumber, quantity));
        stock.setQuantity(quantity);
        Stock saved = stockRepo.save(stock);

        // update read model
        String name = productRepo.findByProductNumber(productNumber)
                .map(p -> p.getName())
                .orElse(null);
        double price = productRepo.findByProductNumber(productNumber)
                .map(p -> p.getPrice())
                .orElse(0.0);

        ProductQuery pq = queryRepo.findByProductNumber(productNumber)
                .orElse(new ProductQuery(productNumber, name, price, quantity));

        pq.setNumberInStock(quantity);
        pq.setName(name);     // may be null if product not created yet
        pq.setPrice(price);
        queryRepo.save(pq);

        return saved;
    }

    @Transactional
    public void delete(String productNumber) {
        stockRepo.deleteByProductNumber(productNumber);

        // if we delete stock, numberInStock becomes 0 in read model
        queryRepo.findByProductNumber(productNumber)
                .ifPresent(pq -> {
                    pq.setNumberInStock(0);
                    queryRepo.save(pq);
                });
    }
}
