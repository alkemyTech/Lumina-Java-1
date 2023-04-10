package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.TransactionDTO;
import com.alkemy.wallet.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionRequestDTO) throws ChangeSetPersister.NotFoundException {
        transactionService.updateTransaction(id, transactionRequestDTO);
        return ResponseEntity.ok("Transaction successfully updated.");
    }
    @PostMapping("/sendUsd/{idSender}")
    public ResponseEntity<?> sendUsd(@PathVariable Long idSender, @RequestBody TransactionDTO transactionRequestDTO) throws Exception {
        try {
            return ResponseEntity.ok(transactionService.sendUsd(transactionRequestDTO, idSender));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
        }
    }


}
