package com.alkemy.wallet.dto;

import com.alkemy.wallet.enums.RoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoleDTO {
    private Long id;
    private RoleEnum name;
    private String description;

}