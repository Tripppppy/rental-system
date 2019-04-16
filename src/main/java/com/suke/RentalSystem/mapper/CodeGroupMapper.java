package com.suke.RentalSystem.mapper;

import com.suke.RentalSystem.core.Mapper;
import com.suke.RentalSystem.entity.CodeGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CodeGroupMapper extends Mapper<CodeGroup> {

    List<CodeGroup> filter(@Param("keyword") String keyword);
}