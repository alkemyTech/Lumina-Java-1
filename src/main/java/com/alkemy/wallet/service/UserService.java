package com.alkemy.wallet.service;

import com.alkemy.wallet.entity.User;
import com.alkemy.wallet.enums.TypeCurrency;
import com.alkemy.wallet.dto.UserDTO;
import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.mapping.UserMapping;
import com.alkemy.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    @Autowired
    UserRepository userRepository;
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public UserDTO saveUser(UserDTO userDTO){
    	
        User userEntity = UserMapping.convertDtoToEntity(userDTO);
        setAccountToUser(userEntity);
        User userEntityDos = userRepository.save(userEntity); 
        UserDTO userDTOResult = UserMapping.convertEntityToDto(userEntityDos);

        return userDTOResult;
    }
    
    private void setAccountToUser(User user) {
        Account USDAcount = new Account();
        Account ARSAcount = new Account();

        USDAcount.setCurrency(TypeCurrency.USD);
        ARSAcount.setCurrency(TypeCurrency.ARS);

        user.getAccounts().add(USDAcount);
        user.getAccounts().add(ARSAcount);

        USDAcount.setBalance(0d);
        ARSAcount.setBalance(0d);

        ARSAcount.setTransactionLimit(0d);
        USDAcount.setTransactionLimit(0d);

        ARSAcount.setUser(user);
        USDAcount.setUser(user);
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> usersDTO = new ArrayList<>();
        for(User userEntity : userRepository.findAll()){
            usersDTO.add(UserMapping.convertEntityToDto(userEntity));
        }
        return usersDTO;
    }

    public UserDTO update(Long id,UserDTO entity) throws Exception{
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            User user = optionalUser.get();

            User userEntity = UserMapping.convertDtoToEntity(entity);
            setAccountToUser(userEntity);
            User userEntityDos = userRepository.save(userEntity);
            UserDTO userDTOResult = UserMapping.convertEntityToDto(userEntityDos);

            user.setFirstName(userDTOResult.getFirstName());
            user.setLastName(userDTOResult.getLastName());
            user.setCreationDate(userDTOResult.getCreationDate());
            user.setUpdateDate(userDTOResult.getUpdateDate());
            return userDTOResult;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
