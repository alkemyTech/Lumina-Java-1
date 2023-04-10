package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.UserDTO;
import com.alkemy.wallet.entity.User;
import com.alkemy.wallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User successfully removed.");
    }
    
    @PostMapping("/auth/register")
    public ResponseEntity<UserDTO> createUser(@Validated @RequestBody UserDTO userDTO){
    	UserDTO userDTOResponse = userService.saveUser(userDTO);
    	return ResponseEntity.ok(userDTOResponse);		
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) throws Exception{
        User user = userService.findById(id);
        if(user==null){
            throw new Exception("User not found with id " + id);
        }
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserDTO>getUser(@PathVariable Long id) throws Exception {
        UserDTO User= userService.getUser(id);
        return ResponseEntity.ok(User);
    }







}
