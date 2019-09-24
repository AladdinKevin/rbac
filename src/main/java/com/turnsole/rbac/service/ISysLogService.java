package com.turnsole.rbac.service;

import com.turnsole.rbac.domain.beans.PageQuery;
import com.turnsole.rbac.domain.beans.PageResult;
import com.turnsole.rbac.domain.model.*;
import com.turnsole.rbac.domain.param.SearchLogParam;

/** 日志记录
 * @author:徐凯
 * @date:2019/9/23,16:53
 * @what I say:just look,do not be be
 */
public interface ISysLogService {

    void saveDeptLog(SysDept before, SysDept after);

    void saveUserLog(SysUser before, SysUser after);

    void saveAclModuleLog(SysAclmodule before, SysAclmodule after);

    void saveAclLog(SysAcl before, SysAcl after);

    void saveRoleLog(SysRole before, SysRole after);

    PageResult<SysLogWithBLOBs> searchPageList(SearchLogParam param, PageQuery pageQuery);

    void recover(int id);

    void add(SysLogWithBLOBs sysLog);
}
