package com.alkemy.wallet.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class UserDTO {

    private Long id;
    @NotNull(message = "El campo firstName no puede ser nulo")
    private String firstName;
    @NotNull(message = "El campo firstName no puede ser nulo")
    private String lastName;
    private String email;
    @NotNull(message = "El campo password no puede ser nulo")
    private String password;
    private String role;
    private List<AccountDTO> accountsDTO;
}
