package com.alkemy.wallet.mapping;

import com.alkemy.wallet.dto.FixedTermDepositsDTO;
import com.alkemy.wallet.entity.FixedTermDeposits;
import java.util.ArrayList;
import java.util.List;

public class FixedTermDepositsMapping {
    public static FixedTermDepositsDTO convertEntityToDto(FixedTermDeposits fixedTermDeposits){
        return FixedTermDepositsDTO.builder()
                .id(fixedTermDeposits.getId())
                .amount(fixedTermDeposits.getAmount())
                .accountId(fixedTermDeposits.getAccount().getId())
                .interest(fixedTermDeposits.getInterest())
                .creationDate(fixedTermDeposits.getCreationDate())
                .closingDate(fixedTermDeposits.getClosingDate())
                .build();
    }

    public static List<FixedTermDepositsDTO> convertEntityListToDtoList(List<FixedTermDeposits> fixedTermDepositEntityList){
        List<FixedTermDepositsDTO> fixedTermDepositsDTOS = new ArrayList<>(fixedTermDepositEntityList.size());
        for(FixedTermDeposits fixedTermDepositEntity : fixedTermDepositEntityList){
            fixedTermDepositsDTOS.add(convertEntityToDto(fixedTermDepositEntity));
        }
        return fixedTermDepositsDTOS;
    }

}
