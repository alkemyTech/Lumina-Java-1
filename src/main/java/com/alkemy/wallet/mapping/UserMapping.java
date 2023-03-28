package com.alkemy.wallet.mapping;
import java.util.*;

import com.alkemy.wallet.entity.User;

import dto.UserDTO;
import com.alkemy.wallet.enums.*;


public class UserMapping {
	
//	public static List<UserDTO> convertListEntityToListDto(List<User> userEntityList){
//		List<UserDTO> userDTOList = new ArrayList<>();
//		for (User users : userEntityList) {
//			UserDTO user = UserMapping.convertEntityToDto(user);
//			userDTOList.add(User);	
//		}
//		return userDTOList;
//		}
//	public static List<User> convertListDtosToListEntities(List<UserDTO> userDTOList){
//	    List<User> userEntityList = new ArrayList<>();
//        for (UserDTO users : userDTOList) {
//            User userEntity = UserMapping.convertDtoToEnttity(users);
//            userEntityList.add(userEntity);
//        }
//        return userEntityList;
//    }
	
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

	