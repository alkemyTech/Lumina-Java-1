package com.alkemy.wallet.repository;

import com.alkemy.wallet.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long> {

}
