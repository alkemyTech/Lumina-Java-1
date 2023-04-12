package com.alkemy.wallet.mapping;

import com.alkemy.wallet.dto.FixedTermDepositsDTO;
import com.alkemy.wallet.entity.FixedTermDeposits;

public class FixedTermDepositsMapping {
     public static FixedTermDepositsDTO convertEntityToDto(FixedTermDeposits fixedTermDepositsEntity) {
        return FixedTermDepositsDTO.builder()
                .id(fixedTermDepositsEntity.getId())
                .amount(fixedTermDepositsEntity.getAmount())
                .creationDate(fixedTermDepositsEntity.getCreationDate())
                .closingDate(fixedTermDepositsEntity.getClosingDate())
                .build();
    }
}
