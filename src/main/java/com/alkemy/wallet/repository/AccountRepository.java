package com.alkemy.wallet.repository;

import com.alkemy.wallet.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query(value = "SELECT * from ljt1.accounts WHERE user_id = :userId AND soft_delete = false", nativeQuery = true)
    List<Account> findUserAccounts(@Param(value = "userId") Long userId);

    @Query(value = "SELECT * FROM ljt1.accounts",
            countQuery = "SELECT count(*) FROM ljt1.accounts",
             nativeQuery = true)
    Page<Account> findAll(Pageable pageable);
}
