package com.alkemy.wallet.mapping;

import com.alkemy.wallet.dto.RoleDTO;
import com.alkemy.wallet.entity.Role;

public class RoleMapping {
    public static RoleDTO convertRoleEntityToDto(Role roleEntity) {
        return RoleDTO.builder()
                .name(roleEntity.getName())
                .build();
    }

    public static Role convertRoleDtoToEntity(RoleDTO roleDTO){
        return Role.builder()
                .roleId(roleDTO.getId())
                .build();
    }
}
