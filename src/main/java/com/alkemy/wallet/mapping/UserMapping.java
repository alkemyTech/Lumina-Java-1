package com.alkemy.wallet.mapping;

import com.alkemy.wallet.dto.AccountDTO;
import com.alkemy.wallet.dto.RoleDTO;
import com.alkemy.wallet.dto.UserDTO;
import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.entity.Role;
import com.alkemy.wallet.entity.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserMapping {
    private static RoleMapping roleMapping = new RoleMapping();
//	private static final AccountMapping accountMapping = new AccountMapping();

    public static UserDTO convertEntityToDto(User userEntity){

//		List<AccountDTO> listAccountDTO = AccountMapping.convertAccountEntityListToDtoList(userEntity.getAccounts());


        return UserDTO.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                //              .accountsDTO(listAccountDTO)
                .build();
    }

//	@Autowired
//    private static PasswordEncoder passwordEncoder;

    public static User convertDtoToEntity(UserDTO userDTO){
        RoleDTO roleDTO= userDTO.getRole();
        Role role=roleMapping.convertRoleDtoToEntity(roleDTO);
        List<AccountDTO> accountDTO= userDTO.getAccountsDTO();
        	List<Account> accounts = AccountMapping.convertAccountDTOListToEntityList(accountDTO);

        return User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .role(role)
                .accounts(accounts)
                .build();
    }
}

	