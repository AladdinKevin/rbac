package com.turnsole.rbac.service;

import com.turnsole.rbac.domain.param.AclModuleParam;

/**
 * @author:徐凯
 * @date:2019/9/19,15:18
 * @what I say:just look,do not be be
 */
public interface ISysAclModuleService {

    void save(AclModuleParam aclmodule);

    void update(AclModuleParam aclmodule);

    void deleteAclModule(int id);
}
