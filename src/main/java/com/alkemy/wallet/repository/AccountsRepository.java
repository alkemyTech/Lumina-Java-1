package com.alkemy.wallet.repository;

import com.alkemy.wallet.entity.Accounts;
import org.springframework.data.repository.CrudRepository;

public interface AccountsRepository extends CrudRepository<Accounts, Long> {

}
