package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.TransactionRequestDTO;
import com.alkemy.wallet.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTransaction(@PathVariable Long id, @RequestBody TransactionRequestDTO transactionRequestDTO) throws ChangeSetPersister.NotFoundException {
        transactionService.updateTransaction(id, transactionRequestDTO);
        return ResponseEntity.ok("Transaction successfully updated.");
    }


}
