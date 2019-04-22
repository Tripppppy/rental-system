package com.suke.RentalSystem.dao;

import com.suke.RentalSystem.core.Mapper;
import com.suke.RentalSystem.model.Code;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CodeMapper extends Mapper<Code> {
    List<Code> listCodeByCond(@Param("keyword") String keyword, @Param("codeGroupCode") String codeGroupCode);
}