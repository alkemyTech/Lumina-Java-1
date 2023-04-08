package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.AccountDTO;
import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.entity.User;
import com.alkemy.wallet.mapping.AccountMapping;
import com.alkemy.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private UserRepository userRepository;
    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountDTO> accountList(Long id)throws Exception {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            throw new Exception("no estas autenticado");
        }
        Optional<User> user = userRepository.findById(id);
        try {
            List<Account> accounts = user.get().getAccounts();
            List<AccountDTO> accountDTOs = new ArrayList<>();
            for (Account account : accounts) {
                AccountDTO accountDTO = AccountMapping.convertAccountEntityToDto(account);
                accountDTOs.add(accountDTO);
            }
            return accountDTOs;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

