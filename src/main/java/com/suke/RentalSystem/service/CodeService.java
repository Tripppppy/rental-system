package com.suke.RentalSystem.service;

import com.suke.RentalSystem.entity.Code;

import java.util.List;

public interface CodeService {
    void saveCode(Code code);

    void deleteByPK(Long id);

    void updateCode(Code code);

    Object findById(Long id);

    List<Code> listCodeByCond(String s, String type);

    List<Code> findAll();

    String getCodeDesc(String type, String code);
}
