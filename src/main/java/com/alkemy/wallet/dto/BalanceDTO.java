package com.alkemy.wallet.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class BalanceDTO {
    private Double usdBalance;
    private Double arsBalance;
    private List<FixedTermDepositsDTO> fixedTermDeposits;
}
