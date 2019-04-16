package com.suke.RentalSystem.mapper;

import com.suke.RentalSystem.core.Mapper;
import com.suke.RentalSystem.entity.Code;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CodeMapper extends Mapper<Code> {
    List<Code> listCodeByCond(@Param("keyword") String keyword, @Param("codeGroupCode") String codeGroupCode);
}