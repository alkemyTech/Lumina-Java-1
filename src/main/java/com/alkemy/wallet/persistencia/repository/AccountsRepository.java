package com.alkemy.wallet.persistencia.repository;

import com.alkemy.wallet.entity.Accounts;
import com.alkemy.wallet.persistencia.crud.AccountsCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AccountsRepository implements com.alkemy.wallet.persistencia.repository.metods.AccountsRepository {
    @Autowired
    private AccountsCrudRepository accountsCrudRepository;

    @Override
    public List<Accounts> getAll() {
        return (List<Accounts>) accountsCrudRepository.findAll();
    }

    @Override
    public Accounts save(Accounts accounts) {
        return accountsCrudRepository.save(accounts);
    }

    @Override
    public Optional<Accounts> getById(Integer accountsId) {
        return accountsCrudRepository.findById(accountsId);
    }

    @Override
    public void deleteById(Integer id) {
        accountsCrudRepository.deleteById(id);
    }
}
