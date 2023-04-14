package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.AccountDTO;
import com.alkemy.wallet.dto.BalanceDTO;
import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/{id}")
    ResponseEntity<List<AccountDTO>> accountList(@PathVariable Long id) throws Exception {
        List<AccountDTO> accounts =accountService.accountList(id);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/paged")
    ResponseEntity<?> accountList(Pageable pageable) throws Exception {
        Page<Account> accounts =accountService.accountList(pageable);
        return ResponseEntity.ok(accounts);
    }
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) throws Exception {
        AccountDTO accountDTOResponse = accountService.createAccount(accountDTO);
        return ResponseEntity.ok(accountDTOResponse);
    }

    @GetMapping("/balance/{id}")
    public ResponseEntity<BalanceDTO> getBalance(@PathVariable Long id) {
        BalanceDTO balanceDTO = accountService.getBalance(id);
        return ResponseEntity.ok(balanceDTO);
    }

}
