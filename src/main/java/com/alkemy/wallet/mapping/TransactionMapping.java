package com.alkemy.wallet.mapping;

import com.alkemy.wallet.dto.AccountDTO;
import com.alkemy.wallet.dto.TransactionRequestDTO;
import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.entity.Transaction;
import com.alkemy.wallet.enums.TransactionTypeEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionMapping {

    public static TransactionRequestDTO convertTransactionEntityToDto(Transaction transaction){
        AccountDTO accountDTO = AccountMapping.convertAccountEntityToDto(transaction.getAccount());
        return TransactionRequestDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .description(transaction.getDescription())
                .account(accountDTO)
                .build();
    }

    public static Transaction convertTransactionDtoToEntity(TransactionRequestDTO transactionRequestDTO){
        Account account = AccountMapping.convertAccountDtoToEntity(transactionRequestDTO.getAccount());
        return Transaction.builder()
                .id(transactionRequestDTO.getId())
                .amount(transactionRequestDTO.getAmount())
                .type(transactionRequestDTO.getType())
                .account(account)
                .transactionDate(LocalDateTime.now())
                .build();
    }

    public static List<TransactionRequestDTO> convertTransactionEntityListToDtoList(List<Transaction> transactions){

        List<TransactionRequestDTO> transactionRequestDTOS = transactions.stream().map(a->convertTransactionEntityToDto(a)).collect(Collectors.toList());
        return transactionRequestDTOS;
    }

    public static List<Transaction> convertTransactionDTOListToEntityList(List<TransactionRequestDTO> transactionRequestDTOS){
        List<Transaction> transactions = transactionRequestDTOS.stream().map(a->convertTransactionDtoToEntity(a)).collect(Collectors.toList());

        return transactions;
    }
}
