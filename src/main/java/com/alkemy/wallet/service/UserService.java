package com.alkemy.wallet.service;

import com.alkemy.wallet.dto.AccountDTO;
import com.alkemy.wallet.entity.User;
import com.alkemy.wallet.enums.TypeCurrencyEnum;
import com.alkemy.wallet.dto.UserDTO;
import com.alkemy.wallet.entity.Account;
import com.alkemy.wallet.mapping.UserMapping;
import com.alkemy.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public ResponseEntity<UserDTO> saveUser(UserDTO userDTO){

        User userEntity = UserMapping.convertDtoToEntity(userDTO);
        setAccountToUser(userEntity);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole(roleService.getRoleByName(userDTO.getRole()));

        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapping.convertEntityToDto(userRepository.save(userEntity)));
    }

    private void setAccountToUser(User user) {
        Account USDAcount = new Account();
        Account ARSAcount = new Account();

        USDAcount.setCurrency(TypeCurrencyEnum.USD);
        ARSAcount.setCurrency(TypeCurrencyEnum.ARS);


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


    @PreAuthorize("hasRole('USER')")
    public UserDTO getUser(Long id) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new Exception("Usuario no autenticado");
        }
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.getEmail().equals(username)) {
                throw new Exception("El usuario autenticado no tiene acceso a este recurso");
            }
            UserDTO userDTO = UserMapping.convertEntityToDto(user);
            return userDTO;
        } else {
            throw new Exception("Usuario " + id + " no encontrado");
        }
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).get();
    }

    public Page<User> getAllUsers(Pageable pageable ) throws Exception {
        try {
            Page<User> usersPage = userRepository.findAll(pageable);
            return usersPage;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }
}
