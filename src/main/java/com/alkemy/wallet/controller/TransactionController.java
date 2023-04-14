package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.TransactionDTO;
import com.alkemy.wallet.entity.Transaction;
import com.alkemy.wallet.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDTO) throws ChangeSetPersister.NotFoundException {
        transactionService.updateTransaction(id, transactionDTO);
        return ResponseEntity.ok("Transaction successfully updated.");
    }

    @PostMapping("/sendUsd/{idSender}")
    public ResponseEntity<?> sendUsd(@PathVariable Long idSender, @RequestBody TransactionDTO transactionDTO) throws Exception {
        try {
            return ResponseEntity.ok(transactionService.sendUsd(transactionDTO, idSender));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/sendArs/{idSender}")
    public ResponseEntity<?> sendArs(@PathVariable Long idSender, @RequestBody TransactionDTO transactionDTO) throws Exception {
        try {
            return ResponseEntity.ok(transactionService.sendArs(transactionDTO, idSender));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST ARS");
        }
    }

    @PostMapping("/payment")
    public ResponseEntity<String> makeAPayment(@RequestBody TransactionDTO transactionDTO) throws Exception {
        transactionService.newPayment(transactionDTO);
        return ResponseEntity.ok("transaction create");
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long id) throws AuthenticationException {
        return ResponseEntity.ok(transactionService.getTransaction(id));
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<?> transactionList(@PathVariable Long UserId) throws Exception {
        try {
            return ResponseEntity.ok(transactionService.transactionDTOList(UserId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("EL USUARIO NO EXISTE");
        }

    }

    @PostMapping("/deposit")
    public ResponseEntity<?> makeDeposit(@RequestBody TransactionDTO transactionDTO) throws Exception {
        try {
            return ResponseEntity.ok(transactionService.makeDeposit(transactionDTO));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/paged")
    ResponseEntity<?> transactionList(Pageable pageable) throws Exception {
        Page<Transaction> transactions =transactionService.transactionsList(pageable);
        return ResponseEntity.ok(transactions);
    }


}
