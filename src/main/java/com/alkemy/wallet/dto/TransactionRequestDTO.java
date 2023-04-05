package com.alkemy.wallet.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionRequestDTO {
    private Long id;
    private Double amount;
    private String type;
    private String description;
}
