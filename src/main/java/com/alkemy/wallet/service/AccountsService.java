package com.alkemy.wallet.service;

import com.alkemy.wallet.entity.Accounts;
import com.alkemy.wallet.persistencia.repository.metods.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountsService {
    @Autowired
    private AccountsRepository accountsRepository;


    public List<Accounts> getAll(){
       return accountsRepository.getAll();
    }

    public Accounts save(Accounts accounts){
        return accountsRepository.save(accounts);
    }

    public Optional<Accounts> getById(Integer accountsId){
        return accountsRepository.getById(accountsId);
    }

    public void deleteById(Integer id) {
        accountsRepository.deleteById(id);
    }



}
