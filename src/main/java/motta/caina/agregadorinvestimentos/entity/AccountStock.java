package motta.caina.agregadorinvestimentos.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_accounts_stock")
public class AccountStock {

    @EmbeddedId
    private AccountStockId id;

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @MapsId("stockId")
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "quantity")
    private Integer quantity;

    public void setId(AccountStockId id) {
        this.id = id;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public AccountStock() {
    }

    public AccountStock(AccountStockId id, Account account, Stock stock, Integer quantity) {
        this.id = id;
        this.account = account;
        this.stock = stock;
        this.quantity = quantity;
    }

    public AccountStockId getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Stock getStock() {
        return stock;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
