package com.alkemy.wallet.controller;

import com.alkemy.wallet.dto.UserDTO;
import com.alkemy.wallet.entity.User;
import com.alkemy.wallet.security.dto.LoginDTO;
import com.alkemy.wallet.security.response.AuthenticationResponse;
import com.alkemy.wallet.security.util.JwTUtil;
import com.alkemy.wallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwTUtil jwTUtil;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User successfully removed.");
    }
    
    @PostMapping("/auth/register")
    public ResponseEntity<UserDTO> createUser(@Validated @RequestBody UserDTO userDTO){
    	return userService.saveUser(userDTO);
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

    @PostMapping(value = "/auth/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginDTO loginDTO) throws Exception {
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        } catch (BadCredentialsException e) {
            return new ResponseEntity(("ok: false"), HttpStatus.BAD_REQUEST);
        }

        final String jwt = jwTUtil.generateToken(auth);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
