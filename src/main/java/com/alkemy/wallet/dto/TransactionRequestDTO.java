package com.alkemy.wallet.dto;

import com.alkemy.wallet.enums.TransactionTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionRequestDTO {
    private Long id;
    private Double amount;
    private TransactionTypeEnum type;
    private String description;
}
