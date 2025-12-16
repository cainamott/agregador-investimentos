package motta.caina.agregadorinvestimentos.service;

import motta.caina.agregadorinvestimentos.controller.dto.AccountResponseDTO;
import motta.caina.agregadorinvestimentos.controller.dto.AccountStockResponseDTO;
import motta.caina.agregadorinvestimentos.controller.dto.AssociateAccountStockDTO;
import motta.caina.agregadorinvestimentos.entity.AccountStock;
import motta.caina.agregadorinvestimentos.entity.AccountStockId;
import motta.caina.agregadorinvestimentos.repository.AccountRepository;
import motta.caina.agregadorinvestimentos.repository.AccountStockRepository;
import motta.caina.agregadorinvestimentos.repository.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    private StockRepository stockRepository;

    private AccountStockRepository accountStockRepository;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;

    }

    public void associateStock(String accountId, AssociateAccountStockDTO associateAccountStockDTO) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(associateAccountStockDTO.stockId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());



        var entity = new AccountStock(
                id,
                account,
                stock,
                associateAccountStockDTO.quantity()
        );

        accountStockRepository.save(entity);
    }

    public List<AccountStockResponseDTO> listStocks(String accountId) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStocks()
                .stream()
                .map(ac -> new AccountStockResponseDTO(ac.getStock().getStockId(), ac.getQuantity(), 0.0))
                .toList();
    }
}
