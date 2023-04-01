package com.alkemy.wallet.service;

import com.alkemy.wallet.entity.User;
import com.alkemy.wallet.enums.TypeCurrency;
import com.alkemy.wallet.dto.UserDTO;
import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.mapping.UserMapping;
import com.alkemy.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
