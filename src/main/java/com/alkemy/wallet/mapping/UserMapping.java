package com.alkemy.wallet.mapping;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.alkemy.wallet.mapping.AccountMapping;
import com.alkemy.wallet.dto.AccountDTO;
import com.alkemy.wallet.dto.UserDTO;
import com.alkemy.wallet.entity.User;

public class UserMapping {
	
	public static UserDTO convertEntityToDto(User userEntity){
//		AccountMapping accountMapping;
		List<AccountDTO> listAccountDTO = AccountMapping.convertEntityListToDtoList(userEntity.getAccounts());
				
       return UserDTO.builder()
        		.id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .creationDate(userEntity.getCreationDate())
                .updateDate(userEntity.getUpdateDate())
                .softDelete(userEntity.isSoftDelete())
                .role(userEntity.getRole())
                .accountsDTO(listAccountDTO)
                .build();
                }
	
    @Autowired
    private static PasswordEncoder passwordEncoder;
	
   	public static User convertDtoToEntity(UserDTO userDTO){
		return User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
    }
	}

	