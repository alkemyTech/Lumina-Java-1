package com.alkemy.wallet.mapping;
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
	
	public static User convertDtoToEntity(UserDTO userDTO){
		return User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }
	}

	