package com.turnsole.rbac.service;

import com.turnsole.rbac.domain.beans.PageQuery;
import com.turnsole.rbac.domain.beans.PageResult;
import com.turnsole.rbac.domain.model.SysUser;
import com.turnsole.rbac.domain.param.UserParam;

import java.util.List;

/**
 * @author:徐凯
 * @date:2019/8/5,17:53
 * @what I say:just look,do not be be
 */
public interface ISysUserService {

    void save(UserParam param);
    void update(UserParam param);
    SysUser findByKeyword(String keyword);
    PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page);
    List<SysUser> getAll();
}
