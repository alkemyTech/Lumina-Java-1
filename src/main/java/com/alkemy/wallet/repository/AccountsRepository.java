package com.alkemy.wallet.repository;

import com.alkemy.wallet.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Account, Long> {

}
