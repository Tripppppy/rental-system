package com.suke.RentalSystem.service.impl;

import com.suke.RentalSystem.core.AbstractService;
import com.suke.RentalSystem.core.ResultCode;
import com.suke.RentalSystem.core.ServiceException;
import com.suke.RentalSystem.dao.UserMapper;
import com.suke.RentalSystem.model.Role;
import com.suke.RentalSystem.model.User;
import com.suke.RentalSystem.service.RoleService;
import com.suke.RentalSystem.service.UserRoleService;
import com.suke.RentalSystem.service.UserService;
import com.suke.RentalSystem.util.QueryUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper tblUserMapper;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RoleService roleService;

    @Override
    public User findByLoginName(String loginName){
        User user = new User();
        user.setLoginName(loginName);
        return findOne(user);
    }

    @Override
    public Boolean saveUser(User user) {
        boolean result = true;
        User checkUser = this.findByLoginName(user.getLoginName());
        if (checkUser != null) {
            result = false;
        } else {
            user.setImageUrl("http://img.kimen.xyz/psb.png");
            super.save(user);
            userRoleService.saveUserRole(user.getId(),user.getRoleIds());
        }
        return result;
    }

    @Override
    public User getUserIdentity(Long userId){
        User user = findById(userId);
        if(user != null){
            List<Role> roles = roleService.findRolesByUserId(userId);
            user.setRoles(roles);
            user.setRoleIds(roles.stream().map(role -> role.getId()).collect(Collectors.toList()));
        }
        return user;
    }

    @Override
    public int updateAdmin(User user) {
        int result = 0;
        User u = findById(user.getId());
        if(StringUtils.isNotBlank(user.getLoginName())){
            u.setLoginName(user.getLoginName());
        }
        if(StringUtils.isNotBlank(user.getName())){
            u.setName(user.getName());
        }
        if(StringUtils.isNotBlank(user.getMobile())){
            u.setMobile(user.getMobile());
        }
        if(StringUtils.isNotBlank(user.getEmail())){
            u.setEmail(user.getEmail());
        }
        if(StringUtils.isNotBlank(user.getImageUrl())){
            u.setImageUrl(user.getImageUrl());
        }

        userRoleService.deleteByUserId(user.getId());
        result = super.updateByPK(u);
        if (user.getRoleIds() != null && user.getRoleIds().size() > 0) {
            userRoleService.saveUserRole(user.getId(),user.getRoleIds());
        }
        return result;
    }

    @Override
    public List<User> listUserPage(String keyword) {
        if (keyword != null) {
            keyword = QueryUtil.replaceSpecialCharactorsForLikeParam(keyword);
        }
        return tblUserMapper.listUserPage(keyword);
    }

    @Override
    public void deleteUser(Long userId) {
        userRoleService.deleteByUserId(userId);
        deleteByPK(userId);
    }

    @Override
    public void updatePassword(User model) {
        User user = findById(model.getId());
        if (user != null) {
            user.setPassword(model.getPassword());
            updateByPK(user);
        }
    }

    @Override
    public List<User> findByPhoneNum(String phoneNum) {
        return tblUserMapper.findByPhoneNum(phoneNum);
    }
}
