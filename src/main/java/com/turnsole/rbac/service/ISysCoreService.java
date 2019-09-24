package com.turnsole.rbac.service;

import com.turnsole.rbac.domain.model.SysAcl;

import java.util.List;

/**
 * @author:徐凯
 * @date:2019/9/21,19:44
 * @what I say:just look,do not be be
 */
public interface ISysCoreService {

    List<SysAcl> getCurrentUserAclList();

    List<SysAcl> getRoleAclList(int roleId);

    List<SysAcl> getUserAclList(int userId);

    boolean hasUrlAcl(String url);

}
