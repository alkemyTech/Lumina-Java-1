package com.alkemy.wallet.service;

import com.alkemy.wallet.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import javax.transaction.Transactional;
import com.alkemy.wallet.entity.User;
import com.alkemy.wallet.repository.UserRepository;

@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImplement implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findOneByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }

		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		Role rol= user.getRole();
		authorities.add(new SimpleGrantedAuthority(rol.getName().toString()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);

	}
}
