package com.alkemy.wallet.service;

import com.alkemy.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
