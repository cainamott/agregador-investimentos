package motta.caina.agregadorinvestimentos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "tb_stock")
public class Stock {

    @Id
    @Column(name = "stock_id")
    private String stockId;

    @Column(name = "description")
    private String description;

    public Stock(String description, String stockId) {
        this.description = description;
        this.stockId = stockId;
    }

    public Stock() {
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
