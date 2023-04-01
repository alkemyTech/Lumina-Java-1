package com.alkemy.wallet.repository;

import com.alkemy.wallet.entity.FixedTermDeposits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface FixedTermDepositsRepository extends JpaRepository<FixedTermDeposits,Long> {
}
