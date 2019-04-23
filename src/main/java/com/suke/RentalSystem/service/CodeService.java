package com.suke.RentalSystem.service;

import com.suke.RentalSystem.core.Service;
import com.suke.RentalSystem.model.Code;

import java.util.List;

/**
 * Created by CodeGenerator on 2018/06/21.
 */
public interface CodeService extends Service<Code> {

    List<Code> listCodeByCond(String keyword, String codeGroupCode);

    List<Code> listByType(String type);

    String getCodeDesc(String type, String code);

    void saveCode(Code code);

    void updateCode(Code code);

    Code findByCode(String brand);
}
