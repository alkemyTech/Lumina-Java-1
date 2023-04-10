package com.alkemy.wallet.mapping;

import com.alkemy.wallet.dto.AccountDTO;
import com.alkemy.wallet.dto.TransactionDTO;
import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.entity.Transaction;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionMapping {

    public static TransactionDTO convertTransactionEntityToDto(Transaction transaction){
        AccountDTO accountDTO = AccountMapping.convertAccountEntityToDto(transaction.getAccount());
        return TransactionDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .description(transaction.getDescription())
                .account(accountDTO)
                .build();
    }

    public static Transaction convertTransactionDtoToEntity(TransactionDTO transactionRequestDTO){
        Account account = AccountMapping.convertAccountDtoToEntity(transactionRequestDTO.getAccount());
        return Transaction.builder()
                .id(transactionRequestDTO.getId())
                .amount(transactionRequestDTO.getAmount())
                .type(transactionRequestDTO.getType())
                .account(account)
                .transactionDate(LocalDateTime.now())
                .build();
    }

    public static List<TransactionDTO> convertTransactionEntityListToDtoList(List<Transaction> transactions){

        List<TransactionDTO> transactionRequestDTOS = transactions.stream().map(a->convertTransactionEntityToDto(a)).collect(Collectors.toList());
        return transactionRequestDTOS;
    }

    public static List<Transaction> convertTransactionDTOListToEntityList(List<TransactionDTO> transactionRequestDTOS){
        List<Transaction> transactions = transactionRequestDTOS.stream().map(a->convertTransactionDtoToEntity(a)).collect(Collectors.toList());

        return transactions;
    }
}
