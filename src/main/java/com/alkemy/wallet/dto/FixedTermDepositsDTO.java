package com.alkemy.wallet.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class FixedTermDepositsDTO {
    private Double amount;
    private Long accountId;
    private Double interest;
    private LocalDateTime creationDate;
    private LocalDateTime closingDate;
}
