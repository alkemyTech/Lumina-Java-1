package com.alkemy.wallet.dto;

import com.alkemy.wallet.entity.FixedTermDeposits;
import com.alkemy.wallet.entity.Transaction;
import com.alkemy.wallet.entity.User;
import com.alkemy.wallet.enums.TypeCurrencyEnum;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountDTO {
	
	 private Long id;	  
	 private TypeCurrencyEnum currency;
	 private Double balance;	
	 private boolean softDelete = Boolean.FALSE;	    
	 private Double transactionLimit;	   
	 private User user; 
	 LocalDateTime creationDate;
	 LocalDateTime updateDate;
	 private Set<Transaction> transactions= new HashSet<>();
	 private Set<FixedTermDeposits> fixedTermDeposits = new HashSet<>();

}
