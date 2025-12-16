package motta.caina.agregadorinvestimentos.repository;

import motta.caina.agregadorinvestimentos.entity.Account;
import motta.caina.agregadorinvestimentos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
}
