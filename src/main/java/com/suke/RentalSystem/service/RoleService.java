package com.suke.RentalSystem.service;

import com.suke.RentalSystem.core.Service;
import com.suke.RentalSystem.model.Role;

import java.util.List;

public interface RoleService extends Service<Role> {

    List<Role> findRolesByUserId(Long userId);

    Role findRoleById(Long id);

    void add(Role role);

    void updateRole(Role role);

    void deleteRole(Long id);

}
