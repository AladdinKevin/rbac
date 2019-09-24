package com.turnsole.rbac.service;

import com.turnsole.rbac.domain.beans.PageQuery;
import com.turnsole.rbac.domain.beans.PageResult;
import com.turnsole.rbac.domain.model.SysAcl;
import com.turnsole.rbac.domain.param.AclParam;

/**
 * @author:徐凯
 * @date:2019/9/20,15:29
 * @what I say:just look,do not be be
 */
public interface ISysAclService {

    void save(AclParam param);

    void update(AclParam param);

    PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery pageQuery);

}
