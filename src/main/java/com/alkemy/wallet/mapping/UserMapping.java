package com.alkemy.wallet.mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.alkemy.wallet.entity.User;
import dto.UserDTO;

public class UserMapping {
	
	public static UserDTO convertEntityToDto(User userEntity){
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
                .accounts(null)
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

	