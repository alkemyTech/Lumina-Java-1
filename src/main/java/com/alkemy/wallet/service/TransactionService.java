package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.TransactionDTO;
import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.entity.Transaction;
import com.alkemy.wallet.enums.TransactionTypeEnum;
import com.alkemy.wallet.enums.TypeCurrency;
import com.alkemy.wallet.mapping.TransactionMapping;
import com.alkemy.wallet.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
        String currency = TypeCurrency.USD.name();
        return send(transactionRequestDTO,idSender, currency);
    }

    public List<TransactionDTO> sendArs(TransactionDTO transactionRequestDTO, Long idSender) throws Exception {
        String currency = TypeCurrency.ARS.name();
        return send(transactionRequestDTO,idSender, currency);
    }

    public List<TransactionDTO> send(TransactionDTO transactionDTO, Long senderUserId,String currency) throws Exception {
        Long reciverAccountId = transactionDTO.getAccountId();
        Long reciverUserId = accountService.findById(reciverAccountId).getUser().getId();

    //    User userSender = userService.getUserById(senderUserId);
     //   User userReceiver = userService.getUserById(receiverUserId);

        existsUser(senderUserId);
        existsUser(reciverUserId);
        equalUsers(senderUserId, reciverUserId);

        Account receiverAccount = accountService.findById(transactionDTO.getAccountId());

        Account senderAccount = accountService.getAccountsOfUser(senderUserId)
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

    public Optional<Transaction> getTransaction(Long id){
       return transactionRepository.findById(id);
    }





}
