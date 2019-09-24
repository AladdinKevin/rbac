package com.turnsole.rbac.service;

import com.turnsole.rbac.domain.dto.AclModuleLevelDto;
import com.turnsole.rbac.domain.dto.DeptLevelDto;

import java.util.List;

/**
 * @author:徐凯
 * @date:2019/8/1,18:47
 * @what I say:just look,do not be be
 */
public interface ISysTreeService {
    List<DeptLevelDto> deptTree();
    List<AclModuleLevelDto> aclModuleTree();
    List<AclModuleLevelDto> roleTree(int roleId);
    List<AclModuleLevelDto> userAlcTree(int userId);
}
