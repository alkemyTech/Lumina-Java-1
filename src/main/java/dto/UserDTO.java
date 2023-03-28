package dto;

import java.time.LocalDateTime;
import java.util.List;

import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.entity.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO {
 
    private Long id;
    private String firstName;
    private String lastName; 
    private String email;
    private String password;
    private Role role;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private boolean softDelete;
    private List<Account> accounts;
        
}
