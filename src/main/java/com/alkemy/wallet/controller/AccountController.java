package com.alkemy.wallet.controller;

import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/accounts")
public class AccountController {

        @Autowired
        AccountService userService;


        @GetMapping("/{id}")
        ResponseEntity<List<Account>> accountList(@PathVariable Long id){
            List<Account> accounts =userService.accountList(id);
            return ResponseEntity.ok(accounts);
        }



    }
