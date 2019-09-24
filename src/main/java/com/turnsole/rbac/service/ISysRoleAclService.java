package com.turnsole.rbac.service;

import java.util.List;

/**
 * @author:徐凯
 * @date:2019/9/23,10:24
 * @what I say:just look,do not be be
 */
public interface ISysRoleAclService {

    void changeRoleAcls(Integer roleId, List<Integer> aclIdList);
}
