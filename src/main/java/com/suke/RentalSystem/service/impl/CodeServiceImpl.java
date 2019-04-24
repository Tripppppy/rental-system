package com.suke.RentalSystem.service.impl;

import com.suke.RentalSystem.core.AbstractService;
import com.suke.RentalSystem.model.Code;
import com.suke.RentalSystem.dao.CodeMapper;
import com.suke.RentalSystem.service.CodeService;
import com.suke.RentalSystem.util.QueryUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class CodeServiceImpl extends AbstractService<Code> implements CodeService {
    @Resource
    private CodeMapper codeMapper;


    @Override
    public List<Code> listByType(String type) {
        if (StringUtils.isEmpty(type)) {
            return null;
        }
        HashMap<String, LinkedHashMap<String, Code>> codeMap = getAllCodes();
        if(codeMap.get(type) == null) {
            return null;
        }
        return new ArrayList<Code>(codeMap.get(type).values());
    }

    @Override
    public List<Code> listCodeByCond(String keyword, String codeGroupCode) {
        if (keyword != null) {
            keyword = QueryUtil.replaceSpecialCharactorsForLikeParam(keyword);
        }
        return codeMapper.listCodeByCond(keyword,codeGroupCode);
    }

    @Override
    public String getCodeDesc(String type, String code) {
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(code))
            return null;
        HashMap<String, LinkedHashMap<String, Code>> codeMap = getAllCodes();
        if(codeMap.get(type) == null) {
            return null;
        }
        Code codeObject = codeMap.get(type).get(code);
        if(codeObject==null)
            return null;
        return codeObject.getName();
    }

    @Override
    public void saveCode(Code code) {
        super.save(code);
    }

    @Override
    public void updateCode(Code code) {
        super.updateByPK(code);
    }

    @Override
    public Code findByCode(String code) {
        Code code1 = new Code();
        code1.setCode(code);
        return findOne(code1);
    }

    public HashMap<String, LinkedHashMap<String, Code>> getAllCodes() {
        List<Code> codes = findAll();
        codes = codes.stream().sorted(Comparator.comparing(Code::getSeqNum)).collect(Collectors.toList());

        HashMap<String, LinkedHashMap<String, Code>> codeMap = new HashMap<>();
        LinkedHashMap<String, Code> codeSubMap = new LinkedHashMap<>();
        for (Code code : codes) {
            if (codeMap.containsKey(code.getCodeGroupCode())) {
                continue;
            } else {
                codeSubMap = new LinkedHashMap<>();
                codeMap.put(code.getCodeGroupCode(), codeSubMap);
            }

            for (Code codei : codes) {
                if (code.getCodeGroupCode().equals(codei.getCodeGroupCode())) {
                    codeSubMap.put(codei.getCode(), codei);
                }
            }
        }
        return codeMap;
    }

}
