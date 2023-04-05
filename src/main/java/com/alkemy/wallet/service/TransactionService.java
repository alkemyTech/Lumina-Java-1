package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.TransactionRequestDTO;
import com.alkemy.wallet.entity.Transaction;
import com.alkemy.wallet.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public void updateTransaction(Long id, TransactionRequestDTO transactionRequestDTO) throws ChangeSetPersister.NotFoundException {
        Transaction transaction = transactionRepository.findById(id).get();
        if (transaction != null){
            transaction.setDescription(transactionRequestDTO.getDescription());
            transactionRepository.save(transaction);
        }else{
            throw new ChangeSetPersister.NotFoundException();
        }
    }

}

