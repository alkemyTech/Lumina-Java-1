package com.alkemy.wallet.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class FixedTermDepositsDTO {
    private Long id;
    @NotNull(message = "El campo firstName no puede ser nulo")
    private Double amount;
    private LocalDateTime creationDate;
    private LocalDateTime closingDate;
}
