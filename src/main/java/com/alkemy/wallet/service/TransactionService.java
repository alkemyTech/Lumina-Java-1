package com.alkemy.wallet.service;

import com.alkemy.wallet.entity.Transaction;
import com.alkemy.wallet.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> findTransactionById(Long id){
        return transactionRepository.findById(id);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
