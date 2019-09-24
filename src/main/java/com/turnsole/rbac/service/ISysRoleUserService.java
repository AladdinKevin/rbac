package com.turnsole.rbac.service;

import com.turnsole.rbac.domain.model.SysUser;

import java.util.List;

/**
 * @author:徐凯
 * @date:2019/9/23,10:27
 * @what I say:just look,do not be be
 */
public interface ISysRoleUserService {

    List<SysUser> getuserListByRoleId(int roleId);

    void changeRoleUsers(int roleId, List<Integer> userIdList);
}
