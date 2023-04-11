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
                .type(transaction.getType().name())
                .description(transaction.getDescription())
                .accountId(transaction.getAccount().getId())
                .build();
    }

    public static List<TransactionDTO> convertTransactionEntityListToDtoList(List<Transaction> transactions){

        List<TransactionDTO> transactionRequestDTOS = transactions.stream().map(a->convertTransactionEntityToDto(a)).collect(Collectors.toList());
        return transactionRequestDTOS;
    }

}
