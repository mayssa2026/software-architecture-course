package com.example.lab10.services;

import com.example.lab10.model.ProductCommand;
import com.example.lab10.model.ProductEvent;
import com.example.lab10.model.ProductQuery;
import com.example.lab10.repositories.ProductCommandRepository;
import com.example.lab10.repositories.ProductEventStore;
import com.example.lab10.repositories.ProductQueryRepository;
import com.example.lab10.repositories.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCommandServiceEventSourcing {

    private final ProductEventStore eventStore;
    private final ProductCommandRepository commandRepo;
    private final StockRepository stockRepo;
    private final ProductQueryRepository queryRepo;

    public ProductCommandServiceEventSourcing(ProductEventStore eventStore,
                                              ProductCommandRepository commandRepo,
                                              StockRepository stockRepo,
                                              ProductQueryRepository queryRepo) {
        this.eventStore = eventStore;
        this.commandRepo = commandRepo;
        this.stockRepo = stockRepo;
        this.queryRepo = queryRepo;
    }

    @Transactional
    public ProductCommand createOrUpdate(String productNumber, String name, Double price) {

        // store event
        ProductEvent event = new ProductEvent(productNumber, "UPDATED", name, price);
        eventStore.save(event);

        // rebuild state from events
        ProductCommand aggregate = rebuild(productNumber);

        ProductCommand saved = commandRepo.save(aggregate);

        // update read model
        int qty = stockRepo.findByProductNumber(productNumber).map(s -> s.getQuantity()).orElse(0);
        ProductQuery pq = queryRepo.findByProductNumber(productNumber)
                .orElse(new ProductQuery(productNumber, name, price, qty));

        pq.setName(saved.getName());
        pq.setPrice(saved.getPrice());
        pq.setNumberInStock(qty);
        queryRepo.save(pq);

        return saved;
    }

    @Transactional
    public void delete(String productNumber) {
        ProductEvent event = new ProductEvent(productNumber, "DELETED", null, null);
        eventStore.save(event);

        commandRepo.deleteByProductNumber(productNumber);
        stockRepo.deleteByProductNumber(productNumber);
        queryRepo.deleteByProductNumber(productNumber);
    }

    private ProductCommand rebuild(String productNumber) {
        return eventStore.findByProductNumberOrderByTimestampAsc(productNumber)
                .stream()
                .reduce(null, (product, ev) -> {
                    if (ev.getType().equals("UPDATED") || ev.getType().equals("CREATED")) {
                        product = new ProductCommand(productNumber, ev.getName(), ev.getPrice());
                    } else if (ev.getType().equals("DELETED")) {
                        product = null;
                    }
                    return product;
                }, (a,b)->b);
    }
}