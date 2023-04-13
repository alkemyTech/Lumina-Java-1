package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.AccountDTO;
import com.alkemy.wallet.dto.BalanceDTO;
import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.entity.FixedTermDeposits;
import com.alkemy.wallet.entity.Transaction;
import com.alkemy.wallet.entity.User;
import com.alkemy.wallet.enums.TypeCurrencyEnum;
import com.alkemy.wallet.mapping.AccountMapping;
import com.alkemy.wallet.mapping.FixedTermDepositsMapping;
import com.alkemy.wallet.repository.AccountRepository;
import com.alkemy.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static com.alkemy.wallet.mapping.AccountMapping.convertAccountEntityToDto;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserService userService;

    public Account findById(Long idSender) {
        return accountRepository.getById(idSender);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Account> accountList(Pageable pageable) throws Exception {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            throw new Exception("No estás autenticado");
        }
        try {
            Page<Account> accountPage;
            accountPage = accountRepository.findAll(pageable);
            return accountPage;
        } catch (NoSuchElementException e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<Account> getUserAccounts(Long userId) {
       return accountRepository.findUserAccounts(userId);
    }

    public void pay(Account accountReceiver, double amount) throws Exception {
        accountReceiver.setBalance(accountReceiver.getBalance()+amount);
        accountRepository.save(accountReceiver);
    }

    public void discount(Account accountSender, double amount) {
        accountSender.setBalance(accountSender.getBalance()-amount);
        accountRepository.save(accountSender);
    }

    public void addTransaction(Long id, Transaction transactionSender) {
        Account sender= accountRepository.findById(id).get();
        sender.getTransactions().add(transactionSender);
        accountRepository.save(sender);

    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountDTO> accountList(Long id) throws Exception {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            throw new Exception("No estás autenticado");
        }
        Optional<User> user = userRepository.findById(id);
        try {
            List<Account> accounts = user.get().getAccounts();
            List<AccountDTO> accountDTOs = new ArrayList<>();
            for (Account account : accounts) {
                AccountDTO accountDTO = convertAccountEntityToDto(account);
                accountDTOs.add(accountDTO);
            }
            return accountDTOs;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public AccountDTO createAccount(AccountDTO accountDTO) throws Exception {

        Account account = AccountMapping.convertAccountDtoToEntity(accountDTO);
        User user = userService.getUserById(accountDTO.getUser().getId());
        Boolean exists = false;
        AccountDTO accountDTOResult = null;

        for (Account accountEntity : user.getAccounts()) {
            if (accountEntity.getCurrency().name().equals(accountDTO.getCurrency().name())) {
                account = accountEntity;
                accountDTOResult = convertAccountEntityToDto(account);
                exists = true;
            }
        }

        if (exists == false) {
            if (accountDTO.getCurrency().name().equals(TypeCurrencyEnum.ARS.name())) {
                account.setCurrency(TypeCurrencyEnum.ARS);
                account.setBalance(0d);
                account.setTransactionLimit(300000d);
                account.setUser(user);
                user.getAccounts().add(account);
                accountDTOResult = convertAccountEntityToDto(accountRepository.save(account));
            } else if (accountDTO.getCurrency().name().equals(TypeCurrencyEnum.USD.name())) {
                account.setCurrency(TypeCurrencyEnum.USD);
                account.setBalance(0d);
                account.setTransactionLimit(1000d);
                account.setUser(user);
                user.getAccounts().add(account);
                accountDTOResult = convertAccountEntityToDto(accountRepository.save(account));
            } else {
                throw new Exception("El tipo de moneda debe ser ARS o USD");
            }
        }

        return accountDTOResult;
    }

    public BalanceDTO getBalance(Long userId) {
        List<Account> accountList = getUserAccounts(userId);

        Account usdAccount = accountList.stream().filter(account -> account.getCurrency().equals(TypeCurrencyEnum.USD)).findAny().get();
        Account arsAccount = accountList.stream().filter(account -> account.getCurrency().equals(TypeCurrencyEnum.ARS)).findAny().get();

        List<FixedTermDeposits> fixedTermDepositList = Stream.concat(usdAccount.getFixedTermDeposits().stream(), arsAccount.getFixedTermDeposits().stream()).toList();

        BalanceDTO balanceDTO = BalanceDTO.builder()
                .usdBalance(usdAccount.getBalance())
                .arsBalance(arsAccount.getBalance())
                .fixedTermDeposits(FixedTermDepositsMapping.convertEntityListToDtoList(fixedTermDepositList))
                .build();

        return balanceDTO;
    }

}

