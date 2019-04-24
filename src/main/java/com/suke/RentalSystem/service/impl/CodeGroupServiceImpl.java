package com.suke.RentalSystem.service.impl;

import com.suke.RentalSystem.core.AbstractService;
import com.suke.RentalSystem.model.CodeGroup;
import com.suke.RentalSystem.dao.CodeGroupMapper;
import com.suke.RentalSystem.service.CodeGroupService;
import com.suke.RentalSystem.util.QueryUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;



@Service
@Transactional
public class CodeGroupServiceImpl extends AbstractService<CodeGroup> implements CodeGroupService {
    @Resource
    private CodeGroupMapper tblCodeGroupMapper;

    @Override
    public List<CodeGroup> filter(String keyword) {
        keyword = QueryUtil.replaceSpecialCharactorsForLikeParam(keyword);
        return tblCodeGroupMapper.filter(keyword);
    }
}
