package motta.caina.agregadorinvestimentos.service;

import motta.caina.agregadorinvestimentos.controller.dto.CreateStockDTO;
import motta.caina.agregadorinvestimentos.entity.Stock;
import motta.caina.agregadorinvestimentos.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDTO createStockDTO){

        var stock = new Stock(
                createStockDTO.stockId(),
                createStockDTO.description()
        );

        stockRepository.save(stock);
    }
}
