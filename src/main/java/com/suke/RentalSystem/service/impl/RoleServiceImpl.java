package com.suke.RentalSystem.service.impl;

import com.suke.RentalSystem.core.AbstractService;
import com.suke.RentalSystem.dao.RoleMapper;
import com.suke.RentalSystem.model.Role;
import com.suke.RentalSystem.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> findRolesByUserId(Long userId){
        return roleMapper.findRolesByUserId(userId);
    }

    @Override
    public Role findRoleById(Long id) {
        return roleMapper.findRoleById(id);
    }

    @Override
    public void add(Role role) {
        save(role);
    }

    @Override
    public void updateRole(Role role) {
        updateByPK(role);
    }

    @Override
    public void deleteRole(Long id) {
        deleteByPK(id);
    }


}
