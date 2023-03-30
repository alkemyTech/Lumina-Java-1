package com.alkemy.wallet.service;

import org.springframework.security.core.userdetails.UserDetailsService;



public interface UserServiceMetod extends UserDetailsService {
    String getName();
    String getUsername();


}
