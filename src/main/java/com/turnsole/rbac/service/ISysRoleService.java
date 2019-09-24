package com.turnsole.rbac.service;

import com.turnsole.rbac.domain.model.SysRole;
import com.turnsole.rbac.domain.model.SysUser;
import com.turnsole.rbac.domain.param.SysRoleParam;

import java.util.List;

/**
 * @author:徐凯
 * @date:2019/9/20,16:08
 * @what I say:just look,do not be be
 */
public interface ISysRoleService {

    void save(SysRoleParam param);

    void update(SysRoleParam param);

    List<SysRole> getAll();

    List<SysRole> getByUserId(int userId);

    List<SysRole> getRoleListByAclId(int aclId);

    List<SysUser> getUserListByRoleList(List<SysRole> roleList);
}
