package com.suke.RentalSystem.service;

import com.suke.RentalSystem.core.Service;
import com.suke.RentalSystem.model.UserRole;

import java.util.List;

public interface UserRoleService extends Service<UserRole> {

    int deleteByUserId(Long userId);

    void saveUserRole(Long userId, List<Long> roleIds);
}
