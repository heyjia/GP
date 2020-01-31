package com.heihei.bookrecommendsystem.service.impl;

import com.heihei.bookrecommendsystem.dao.PrivilegeMapper;
import com.heihei.bookrecommendsystem.entity.PrivilegeDO;
import com.heihei.bookrecommendsystem.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    @Autowired
    PrivilegeMapper privilegeMapper;
    @Override
    public List<PrivilegeDO> listPrivilege() {
        return privilegeMapper.selectAll();
    }
}
