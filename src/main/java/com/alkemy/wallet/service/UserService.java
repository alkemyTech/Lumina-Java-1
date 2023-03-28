package com.alkemy.wallet.service;

import com.alkemy.wallet.entity.User;
import com.alkemy.wallet.mapping.UserMapping;
import com.alkemy.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dto.UserDTO;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public UserDTO saveUser(UserDTO userDTO){

        User userEntity = UserMapping.convertDtoToEntity(userDTO);
        User userEntityDos = userRepository.save(userEntity); 
        UserDTO userDTOResult = UserMapping.convertEntityToDto(userEntityDos);

        return userDTOResult;
    }

}
