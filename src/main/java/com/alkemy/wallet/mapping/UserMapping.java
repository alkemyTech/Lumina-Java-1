package com.alkemy.wallet.mapping;

import com.alkemy.wallet.dto.AccountDTO;
import com.alkemy.wallet.dto.UserDTO;
import com.alkemy.wallet.entity.User;

import java.util.List;

public class UserMapping {
    private static RoleMapping roleMapping = new RoleMapping();

    public static UserDTO convertEntityToDto(User userEntity){

		List<AccountDTO> listAccountDTO = AccountMapping.convertAccountEntityListToDtoList(userEntity.getAccounts());


        return UserDTO.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(userEntity.getRole().getName().toString())
                .accountsDTO(listAccountDTO)
                .build();
    }


    public static User convertDtoToEntity(UserDTO userDTO){

        return User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }
}

	