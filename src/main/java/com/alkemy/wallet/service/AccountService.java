package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.TransactionRequestDTO;
import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.entity.Transaction;
import com.alkemy.wallet.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    public Account findById(Long idSender) {
        return accountRepository.getById(idSender);
    }

    public void pay(Account accountReceiver, double amount) throws Exception {
        transactionValidation(amount,accountReceiver);
        accountReceiver.setBalance(accountReceiver.getBalance()+amount);
        accountRepository.save(accountReceiver);
    }

    private void transactionValidation(double amount, Account accountReceiver) throws Exception {
        if(accountReceiver.getBalance()<amount){
            throw new Exception("DINERO INSUFICIENTE");
        }
        if (accountReceiver.getTransactionLimit()<amount){
            throw  new Exception("LIMITE DE TRANSFERENCIA EXEDIDO");
        }
    }

    public void discount(Account accountSender, Double amount) {
        accountSender.setBalance(accountSender.getBalance()-amount);
        accountRepository.save(accountSender);
    }

    public void addTransaction(Long id, Transaction transactionSender) {
        Account sender= accountRepository.findById(id).get();
        sender.getTransactions().add(transactionSender);
        accountRepository.save(sender);

    }
}
