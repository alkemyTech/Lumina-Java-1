package com.alkemy.wallet.controller;

import com.alkemy.wallet.entity.Role;
import com.alkemy.wallet.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
@Validated
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/auth/register")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return ResponseEntity.ok(roleService.saveRole(role));
    }

}
