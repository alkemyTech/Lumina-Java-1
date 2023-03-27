package com.alkemy.wallet.persistencia.repository.metods;

import com.alkemy.wallet.entity.Accounts;

import java.util.List;
import java.util.Optional;

public interface AccountsRepository {
    List<Accounts> getAll();
    Accounts save(Accounts accounts);
    Optional<Accounts> getById(Integer accountsId);
    void deleteById(Integer id);
}
