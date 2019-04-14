package com.suke.RentalSystem.service.impl;

import com.suke.RentalSystem.entity.Code;
import com.suke.RentalSystem.mapper.CodeMapper;
import com.suke.RentalSystem.service.CodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CodeServiceImpl implements CodeService {

    @Resource
    private CodeMapper codeMapper;

    @Override
    public void saveCode(Code code) {
        codeMapper.saveCode(code);
    }

    @Override
    public void deleteByPK(Long id) {
        codeMapper.deleteByPK(id);
    }

    @Override
    public void updateCode(Code code) {
        codeMapper.updateCode(code);
    }

    @Override
    public Object findById(Long id) {
        return codeMapper.findById(id);
    }

    @Override
    public List<Code> listCodeByCond(String s, String type) {
        return codeMapper.listCodeByCond(s, type);
    }

    @Override
    public List<Code> findAll() {
        return codeMapper.findAll();
    }

    @Override
    public String getCodeDesc(String type, String code) {
        return codeMapper.getCodeDesc(type, code);
    }
}
