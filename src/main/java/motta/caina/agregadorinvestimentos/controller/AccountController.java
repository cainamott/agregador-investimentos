package motta.caina.agregadorinvestimentos.controller;

import motta.caina.agregadorinvestimentos.controller.dto.AccountStockResponseDTO;
import motta.caina.agregadorinvestimentos.controller.dto.AssociateAccountStockDTO;
import motta.caina.agregadorinvestimentos.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String id,
                                               @RequestBody AssociateAccountStockDTO associateAccountStockDTO) {

        accountService.associateStock(id, associateAccountStockDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{account}/stocks")
    public ResponseEntity<List<AccountStockResponseDTO>> getStocks(@PathVariable("accountId") String id) {

        var stocks = accountService.listStocks(id);

        return ResponseEntity.ok().build();
    }
}