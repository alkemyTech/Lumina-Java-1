package com.alkemy.wallet.repository;

import com.alkemy.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findOneByEmail(String email);

	User findByEmailOrPassword(String email, String password);

}
