package com.suke.RentalSystem.dao;

import com.suke.RentalSystem.core.Mapper;
import com.suke.RentalSystem.model.CodeGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CodeGroupMapper extends Mapper<CodeGroup> {

    List<CodeGroup> filter(@Param("keyword") String keyword);
}