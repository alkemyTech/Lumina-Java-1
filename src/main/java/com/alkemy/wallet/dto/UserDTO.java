package com.alkemy.wallet.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.alkemy.wallet.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
public class UserDTO {
 
    private Long id;
    @NotNull(message = "El campo firstName no puede ser nulo")
    @Pattern(regexp = "[a-zA-Z ]{2,64}", message = "Debe contener solo letras y no puede estar vacio.")
    private String firstName;
    @NotNull(message = "El campo firstName no puede ser nulo")
    @Pattern(regexp = "[a-zA-Z ]{2,64}", message = "Debe contener solo letras y no puede estar vacio.")
    private String lastName; 
    @Pattern(regexp = "^\\\\w+[\\\\.\\\\w]*@[\\\\w]+([\\\\.\\\\w]+)+$", message = "Ingrese un email valido")
    private String email;
    @NotNull(message = "El campo password no puede ser nulo")
    @Pattern(regexp = "^(?=.[a-z])(?=.[A-Z])(?=.\\d)(?=.[@#$%^&+=!])(?=.*[^\\s]).{8,20}$",
            message = "La contraseña debe tener: \n" +
                    " -al menos una letra mayúscula.\n" +
                    " -al menos una letra minúscula.\n" +
                    " -al menos un número.\n" +
                    " -al menos un carácter especial, como @, #, $, etc.\n" +
                    " -una longitud mínima y máxima especificada."
    )
    private String password;
    private Role role;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private boolean softDelete;
    private List<AccountDTO> accountsDTO;
        
}
