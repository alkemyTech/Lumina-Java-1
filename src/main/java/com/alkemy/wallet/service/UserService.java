package com.alkemy.wallet.service;

import com.alkemy.wallet.entity.User;
import com.alkemy.wallet.enums.TypeCurrency;
import com.alkemy.wallet.dto.UserDTO;
import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.mapping.UserMapping;
import com.alkemy.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public UserDTO saveUser(UserDTO userDTO){
    	
        User userEntity = UserMapping.convertDtoToEntity(userDTO);
        setAccountToUser(userEntity);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        User userEntityDos = userRepository.save(userEntity);
        UserDTO userDTOResult = UserMapping.convertEntityToDto(userEntityDos);

        return userDTOResult;
    }
    
    private void setAccountToUser(User user) {
        Account USDAcount = new Account();
        Account ARSAcount = new Account();

        USDAcount.setCurrency(TypeCurrency.USD);
        ARSAcount.setCurrency(TypeCurrency.ARS);


        USDAcount.setBalance(0d);
        ARSAcount.setBalance(0d);

        ARSAcount.setTransactionLimit(0d);
        USDAcount.setTransactionLimit(0d);

        ARSAcount.setUser(user);
        USDAcount.setUser(user);

        user.getAccounts().add(USDAcount);
        user.getAccounts().add(ARSAcount);
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> usersDTO = new ArrayList<>();
        for(User userEntity : userRepository.findAll()){
            usersDTO.add(UserMapping.convertEntityToDto(userEntity));
        }
        return usersDTO;
    }
    public UserDTO updateUser(Long id, UserDTO userDTO) throws ChangeSetPersister.NotFoundException {
        User userEntity = userRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        User user = UserMapping.convertDtoToEntity(userDTO);
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setPassword(user.getPassword());
        userEntity.setUpdateDate(user.getUpdateDate());
        userEntity.setCreationDate(user.getCreationDate());
        userEntity.setSoftDelete(user.isSoftDelete());
        userEntity.setAccounts(user.getAccounts());
        User savedUser = userRepository.save(userEntity);
        UserDTO userDTOResult = UserMapping.convertEntityToDto(savedUser);
        return userDTOResult;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}
