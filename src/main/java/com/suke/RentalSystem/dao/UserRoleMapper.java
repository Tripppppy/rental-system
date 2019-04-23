package com.suke.RentalSystem.dao;

import com.suke.RentalSystem.core.Mapper;
import com.suke.RentalSystem.model.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper extends Mapper<UserRole> {

    void save(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

    int deleteByUserId(Long userId);
}