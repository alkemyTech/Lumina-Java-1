package com.alkemy.wallet.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class FixedTermDepositsDTO {
    private Long id;
    @NotNull(message = "El campo firstName no puede ser nulo")
    private Double amount;
    private Double totalAmount;
    private Long accountId;
    private Double interest;
    private LocalDateTime creationDate;
    private LocalDateTime closingDate;
}
