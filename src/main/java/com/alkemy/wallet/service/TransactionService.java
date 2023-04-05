package com.alkemy.wallet.service;

import com.alkemy.wallet.entity.Transaction;
import com.alkemy.wallet.enums.TransactionTypeEnum;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    public Transaction crearTransaction(Double dinero, String type){
        if(dinero > 0){
            Transaction transaction = new Transaction();
            transaction.setType(TransactionTypeEnum.valueOf(type));
        }
        return transaction;

    }
}
