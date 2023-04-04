package com.alkemy.wallet.service;

import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.entity.User;
import com.alkemy.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private UserRepository userRepository;

    public List<Account> accountList(Long id) {
        Optional<User> user = userRepository.findById(id);
        try {
            return user.get().getAccounts();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }











}
