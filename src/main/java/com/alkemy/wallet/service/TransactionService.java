package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.TransactionDTO;
import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.entity.Transaction;
import com.alkemy.wallet.entity.User;
import com.alkemy.wallet.enums.TransactionTypeEnum;
import com.alkemy.wallet.enums.TypeCurrencyEnum;
import com.alkemy.wallet.mapping.TransactionMapping;
import com.alkemy.wallet.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    public void updateTransaction(Long id, TransactionDTO transactionRequestDTO) throws ChangeSetPersister.NotFoundException {
        Transaction transaction = transactionRepository.findById(id).get();
        if (transaction != null){
            transaction.setDescription(transactionRequestDTO.getDescription());
            transactionRepository.save(transaction);
        }else{
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    public List<TransactionDTO> sendUsd(TransactionDTO transactionRequestDTO, Long idSender) throws Exception {
        String currency = TypeCurrencyEnum.USD.name();
        return send(transactionRequestDTO,idSender, currency);
    }

    public List<TransactionDTO> sendArs(TransactionDTO transactionRequestDTO, Long idSender) throws Exception {
        String currency = TypeCurrencyEnum.ARS.name();
        return send(transactionRequestDTO,idSender, currency);
    }

    public List<TransactionDTO> send(TransactionDTO transactionDTO, Long senderUserId,String currency) throws Exception {
        Long reciverAccountId = transactionDTO.getAccountId();
        Long reciverUserId = accountService.findById(reciverAccountId).getUser().getId();

        existsUser(senderUserId);
        existsUser(reciverUserId);
        equalUsers(senderUserId, reciverUserId);

        Account receiverAccount = accountService.findById(transactionDTO.getAccountId());

        Account senderAccount = accountService.getUserAccounts(senderUserId)
                .stream()
                .filter(account -> account.getCurrency().name().equals(currency))
                .findAny()
                .get();

        return generateTransaction(senderAccount, receiverAccount, transactionDTO);
    }

    private void equalUsers(Long senderUserId, Long receiverUserId) throws Exception {
        if(senderUserId == receiverUserId){
            throw new Exception("TRANSACCION INVALIDA");
        }
    }

    public void existsUser(Long userSenderId) throws Exception {

        Long user = userService.getUserById(userSenderId).getId();
        if  (user == null){
            throw new Exception("USUAIRO INEXISTENTE");
        }
    }

    private List<TransactionDTO> generateTransaction(Account accountSender, Account accountReceiver, TransactionDTO transactionDTO) throws Exception {

        validateAmount(accountSender, transactionDTO);
        accountService.pay(accountReceiver,transactionDTO.getAmount());
        accountService.discount(accountSender,transactionDTO.getAmount());

        Transaction transactionSender=generateTransactionSender(accountSender,accountReceiver,transactionDTO);
        Transaction transactionReceiver=generateTransactionReceiver(accountSender,accountReceiver,transactionDTO);

        accountService.addTransaction(accountSender.getId(),transactionSender);
        accountService.addTransaction(accountReceiver.getId(),transactionReceiver);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transactionSender);
        transactions.add(transactionReceiver);

        return TransactionMapping.convertTransactionEntityListToDtoList(transactions);
    }

    private void validateAmount(Account senderAccount, TransactionDTO transactionDTO) throws Exception {
        if(senderAccount.getBalance() < transactionDTO.getAmount()){
            throw new Exception("SALDO INSUFICIENTE");
        }
        if(senderAccount.getTransactionLimit() < transactionDTO.getAmount()){
            throw new Exception("LIMITE DE TRANSACCION EXCEDIDO");
        }
    }

    private Transaction generateTransactionReceiver(Account accountSender, Account accountReceiver, TransactionDTO transactionDTO) {
        StringBuilder descriptionTransactionReceiver= new StringBuilder();
        descriptionTransactionReceiver.append("ACREDITACION DE ")
                .append(transactionDTO.getAmount())
                .append(" A LA CUENTA ")
                .append(accountReceiver.getUser().getFirstName())
                .append(accountReceiver.getUser().getLastName())
                .append(" POR LA CUENTA ")
                .append(accountSender.getUser().getFirstName())
                .append(accountSender.getUser().getLastName())
                .append(" EL DIA ")
                .append(LocalDate.now());

        Transaction transactionReceiver = Transaction.builder()
                .amount(transactionDTO.getAmount())
                .type(TransactionTypeEnum.INCOME)
                .description(descriptionTransactionReceiver.toString())
                .account(accountReceiver)
                .transactionDate(LocalDateTime.now())
                .build();
        transactionRepository.save(transactionReceiver);
        accountService.addTransaction(accountReceiver.getId(),transactionReceiver);
        return transactionReceiver;
    }

    private Transaction generateTransactionSender(Account accountSender, Account accountReceiver, TransactionDTO transactionRequestDTO) {
        StringBuilder descriptionTransactionReceiver= new StringBuilder();
        descriptionTransactionReceiver.append(" SE REALIZO UN DESCUENTO DE ")
                .append(transactionRequestDTO.getAmount())
                .append(" A LA CUENTA ")
                .append(accountSender.getUser().getFirstName())
                .append(accountSender.getUser().getLastName())
                .append(" POR ACREDITACION A LA CUENTA ")
                .append(accountReceiver.getUser().getFirstName())
                .append(accountReceiver.getUser().getLastName())
                .append(" EL DIA ")
                .append(LocalDate.now());

        Transaction transactionSender = Transaction.builder()
                .amount(transactionRequestDTO.getAmount())
                .type(TransactionTypeEnum.PAYMENT)
                .description(descriptionTransactionReceiver.toString())
                .account(accountSender)
                .transactionDate(LocalDateTime.now())
                .build();
        transactionRepository.save(transactionSender);
        accountService.addTransaction(accountSender.getId(),transactionSender);
        return transactionSender;
    }

    public void newPayment(TransactionDTO transactionRequestDTO) throws IllegalArgumentException {

        if (transactionRequestDTO.getAmount() <= 0 || !transactionRequestDTO.getType().equals("PAYMENT")) {
            throw new IllegalArgumentException("detalles de la transaccion invalidos");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequestDTO.getAmount());
        transaction.setType(TransactionTypeEnum.PAYMENT);
        transaction.setDescription(transactionRequestDTO.getDescription());
        Account account = accountService.findById(transactionRequestDTO.getId());
        transaction.setAccount(account);

        transactionRepository.save(transaction);
    }

    @PreAuthorize("isAuthenticated()")
    public TransactionDTO getTransaction(Long id) throws AuthenticationException {
        if(SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new AuthenticationException("User is not authenticated"){};
        }
        Transaction transaction= transactionRepository.findById(id).get();
        return TransactionMapping.convertTransactionEntityToDto(transaction);
    }

    public  List<TransactionDTO> transactionDTOList(Long id) {
        User user = userService.getUserById(id);
        List<Account> accounts = user.getAccounts();
        List<Transaction> transactionList = accounts.stream().flatMap(t->t.getTransactions().stream()).collect(Collectors.toList());
        List<TransactionDTO> transactionDTOs = TransactionMapping.convertTransactionEntityListToDtoList(transactionList);
        return transactionDTOs;
    }

    public TransactionDTO makeDeposit(TransactionDTO transactionDTO) throws Exception {
        Account accountEntity = accountService.findById(transactionDTO.getAccountId());
        if (transactionDTO.getAmount() <= 0) {
            throw new Exception("MONTO INVALIDO");
        }
        accountService.pay(accountEntity, transactionDTO.getAmount());
        return TransactionMapping.convertTransactionEntityToDto(generateDeposit(accountEntity, transactionDTO));
    }

    private Transaction generateDeposit(Account accountEntity, TransactionDTO transactionDTO) {
        StringBuilder description = new StringBuilder();
        description.append("SE REALIZO UN DEPOSITO DE ")
                .append(transactionDTO.getAmount())
                .append(" EL DIA ")
                .append(LocalDate.now().toString());

       return transactionRepository.save(Transaction.builder()
                .amount(transactionDTO.getAmount())
                .type(TransactionTypeEnum.DEPOSIT)
                .description(description.toString())
                .account(accountService.findById(accountEntity.getId()))
                .transactionDate(LocalDateTime.now())
                .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<Transaction> transactionsList(Pageable pageable) throws Exception {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            throw new Exception("No estás autenticado");
        }
        try {
            Page<Transaction> transactionsPage;
            transactionsPage = transactionRepository.findAll(pageable);
            return transactionsPage;
        } catch (NoSuchElementException e) {
            throw new Exception(e.getMessage());
        }
    }

}
