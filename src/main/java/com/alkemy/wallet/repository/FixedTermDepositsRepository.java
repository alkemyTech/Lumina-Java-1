package com.alkemy.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.wallet.entity.FixedTermDeposits;

public interface FixedTermDepositsRepository extends JpaRepository<FixedTermDeposits,Long> {
}
