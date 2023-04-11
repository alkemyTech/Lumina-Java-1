package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.TransactionDTO;
import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.entity.Transaction;
import com.alkemy.wallet.entity.User;
import com.alkemy.wallet.enums.TransactionTypeEnum;
import com.alkemy.wallet.enums.TypeCurrency;
import com.alkemy.wallet.mapping.TransactionMapping;
import com.alkemy.wallet.repository.AccountRepository;
import com.alkemy.wallet.repository.TransactionRepository;
import com.alkemy.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountService accountService;

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

    private List<TransactionDTO> send(TransactionDTO transactionRequestDTO, Long idSender, String currency) throws Exception {
        Account accountSender = accountService.findById(idSender);
        Account accountReceiver = accountService.findById(transactionRequestDTO.getAccount().getId());

        return generateTransaction(accountSender,accountReceiver,transactionRequestDTO);
    }

    private List<TransactionDTO> generateTransaction(Account accountSender, Account accountReceiver, TransactionDTO transactionRequestDTO) throws Exception {

        //Acreditar account receiver
        accountService.pay(accountReceiver,transactionRequestDTO.getAmount());
        //Descontar a account sender
        accountService.discount(accountSender,transactionRequestDTO.getAmount());

        //Generar la transaccion para sender
        Transaction transactionSender=generateTransactionSender(accountSender,accountReceiver,transactionRequestDTO);
        //Generar la transaccion para receiver
        Transaction transactionReceiver=generateTransactionReceiver(accountSender,accountReceiver,transactionRequestDTO);

        accountService.addTransaction(accountSender.getId(),transactionSender);
        accountService.addTransaction(accountReceiver.getId(),transactionReceiver);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transactionSender);
        transactions.add(transactionReceiver);

        return TransactionMapping.convertTransactionEntityListToDtoList(transactions);
    }

    private Transaction generateTransactionReceiver(Account accountSender, Account accountReceiver, TransactionDTO transactionRequestDTO) {
        StringBuilder descriptionTransactionReceiver= new StringBuilder();
        descriptionTransactionReceiver.append("ACREDITACION DE ")
                .append(transactionRequestDTO.getAmount())
                .append("A LA CUENTA ")
                .append(accountReceiver.getUser().getFirstName())
                .append(accountReceiver.getUser().getLastName())
                .append("POR LA CUENTA ")
                .append(accountSender.getUser().getFirstName())
                .append(accountSender.getUser().getLastName())
                .append("EL DIA ")
                .append(LocalDate.now());

        Transaction transactionReceiver = Transaction.builder()
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
        descriptionTransactionReceiver.append("DESCUENTO DE ")
                .append(transactionRequestDTO.getAmount())
                .append("A LA CUENTA ")
                .append(accountSender.getUser().getFirstName())
                .append(accountSender.getUser().getLastName())
                .append("POR ACREDITACION A LA CUENTA ")
                .append(accountReceiver.getUser().getFirstName())
                .append(accountReceiver.getUser().getLastName())
                .append("EL DIA ")
                .append(LocalDate.now());

        Transaction transactionSender = Transaction.builder()
                .type(TransactionTypeEnum.PAYMENT)
                .description(descriptionTransactionReceiver.toString())
                .account(accountSender)
                .build();
        transactionRepository.save(transactionSender);
        accountService.addTransaction(accountSender.getId(),transactionSender);
        return transactionSender;
    }

    public void newPayment(TransactionDTO transactionRequestDTO) throws IllegalArgumentException {

        if (transactionRequestDTO.getAmount() <= 0 || !transactionRequestDTO.getType().equals(TransactionTypeEnum.PAYMENT)) {
            throw new IllegalArgumentException("detalles de la transaccion invalidos");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequestDTO.getAmount());
        transaction.setType(transactionRequestDTO.getType());
        transaction.setDescription(transactionRequestDTO.getDescription());
        Account account = accountService.findById(transactionRequestDTO.getId());
        transaction.setAccount(account);

        transactionRepository.save(transaction);

    }





}
