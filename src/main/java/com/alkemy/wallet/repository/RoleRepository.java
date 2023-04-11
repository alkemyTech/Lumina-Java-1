package com.alkemy.wallet.repository;

import com.alkemy.wallet.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM ljt1.roles WHERE name =:nombreRole",nativeQuery = true)
    Role getRoleByName(@Param(value = "nombreRole") String nombreRole);
    
}
