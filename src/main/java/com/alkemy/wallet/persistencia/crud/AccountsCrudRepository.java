package com.alkemy.wallet.persistencia.crud;

import com.alkemy.wallet.entity.Accounts;
import org.springframework.data.repository.CrudRepository;

public interface AccountsCrudRepository extends CrudRepository<Accounts, Integer> {

}
