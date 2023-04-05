package com.alkemy.wallet.mapping;

import com.alkemy.wallet.dto.AccountDTO;
import com.alkemy.wallet.entity.Account;
import java.util.List;
import java.util.stream.Collectors;

public class AccountMapping {

	    public static AccountDTO convertAccountEntityToDto(Account account){
	        return AccountDTO.builder()
	                .id(account.getId())
	                .currency(account.getCurrency())
	                .transactionLimit(account.getTransactionLimit())
	                .balance(account.getBalance())
	                .user(account.getUser())
	                .creationDate(account.getCreationDate())
	                .updateDate(account.getUpdateDate())
	                .softDelete(account.isSoftDelete())
	                .transactions(null)
	                .fixedTermDeposits(null)
	                .build();
	    }

	    public static Account convertAccountDtoToEntity(AccountDTO accountDTO){
	        return Account.builder()
	                .currency(accountDTO.getCurrency())
	                .transactionLimit(accountDTO.getTransactionLimit())
	                .balance(accountDTO.getBalance())
	                .user(accountDTO.getUser())
	                .build();
	    }

	    public static List<AccountDTO> convertAccountEntityListToDtoList(List<Account> accounts){
	    	
	    	List<AccountDTO> accountDTOList = accounts.stream().map(a->convertAccountEntityToDto(a)).collect(Collectors.toList());
	       return accountDTOList;
	    }

	    public static List<Account> convertAccountDTOListToEntityList(List<AccountDTO> accountsDTO){
	    	List<Account> accountList = accountsDTO.stream().map(a->convertAccountDtoToEntity(a)).collect(Collectors.toList());
	        
	        return accountList;
	    }
}


