package motta.caina.agregadorinvestimentos.repository;

import motta.caina.agregadorinvestimentos.entity.Stock;
import motta.caina.agregadorinvestimentos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
}
