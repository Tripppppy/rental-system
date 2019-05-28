package com.suke.RentalSystem.service.impl;

import com.suke.RentalSystem.core.AbstractService;
import com.suke.RentalSystem.dao.UserRoleMapper;
import com.suke.RentalSystem.model.UserRole;
import com.suke.RentalSystem.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserRoleServiceImpl extends AbstractService<UserRole> implements UserRoleService {
    @Resource
    private UserRoleMapper tblUserRoleMapper;

    @Override
    public int deleteByUserId(Long userId) {
        return tblUserRoleMapper.deleteByUserId(userId);
    }

    @Override
    public void saveUserRole(Long userId, List<Long> roleIds) {
        if (roleIds == null) {
            roleIds = new ArrayList<>();
        }
        if (roleIds.isEmpty()) {
            roleIds.add(30L);
        }
        tblUserRoleMapper.save(userId,roleIds);
    }
}
