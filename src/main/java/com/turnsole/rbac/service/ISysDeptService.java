package com.turnsole.rbac.service;

import com.turnsole.rbac.domain.param.DeptParam;

/**
 * @author:徐凯
 * @date:2019/8/1,17:27
 * @what I say:just look,do not be be
 */
public interface ISysDeptService {
    void save(DeptParam param);

    void update(DeptParam dept);

    void deleteDept(int deptId);

}
