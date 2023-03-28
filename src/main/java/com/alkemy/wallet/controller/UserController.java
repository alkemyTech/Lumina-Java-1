package com.alkemy.wallet.controller;

import com.alkemy.wallet.service.UserService;
import dto.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User successfully removed.");
    }
    
    @PostMapping 
    public ResponseEntity<UserDTO> generete(@RequestBody UserDTO userDTO){
    	UserDTO userDTOResponse = userService.saveUser(userDTO);
    	return ResponseEntity.ok(userDTOResponse);		
    }
}
