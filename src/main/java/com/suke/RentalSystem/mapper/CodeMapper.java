package com.suke.RentalSystem.mapper;

import com.suke.RentalSystem.entity.Code;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeMapper {
    void saveCode(Code code);

    void deleteByPK(Long id);

    void updateCode(Code code);

    Object findById(Long id);

    List<Code> listCodeByCond(String s, String type);

    List<Code> findAll();

    String getCodeDesc(String type, String code);
}
