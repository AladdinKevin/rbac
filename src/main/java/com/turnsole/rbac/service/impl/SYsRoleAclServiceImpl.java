package com.turnsole.rbac.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.turnsole.rbac.common.RequestHolder;
import com.turnsole.rbac.domain.beans.LogType;
import com.turnsole.rbac.domain.model.SysLogWithBLOBs;
import com.turnsole.rbac.domain.model.SysRoleAcl;
import com.turnsole.rbac.mapper.SysRoleAclMapper;
import com.turnsole.rbac.service.ISysLogService;
import com.turnsole.rbac.service.ISysRoleAclService;
import com.turnsole.rbac.util.JsonMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author:徐凯
 * @date:2019/9/23,10:24
 * @what I say:just look,do not be be
 */
@Service
public class SYsRoleAclServiceImpl implements ISysRoleAclService {

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;
    @Autowired
    private ISysLogService sysLogService;

    @Override
    public void changeRoleAcls(Integer roleId, List<Integer> aclIdList) {
        //取出当前角色 分配过的 权限id
        List<Integer> originAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        //判断要修改的权限点和当前角色已有权限点是否一致,一致不做任何处理
        if (originAclIdList.size() == aclIdList.size()) {
            Set<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
            Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
            originAclIdSet.removeAll(aclIdSet);
            if (CollectionUtils.isEmpty(originAclIdSet)){
                return;
            }
        }
        //不一致,做更新操作
        updateRoleAcls(roleId,aclIdList);
        saveRoleAclLog(roleId,originAclIdList,aclIdList);
    }

    @Transactional
    public void updateRoleAcls(int roleId, List<Integer> aclIdList){
        sysRoleAclMapper.deleteByRoleId(roleId);
        if (CollectionUtils.isEmpty(aclIdList)){
            return;
        }
        List<SysRoleAcl> roleAclList = Lists.newArrayList();
        for (Integer aclId : aclIdList){
            SysRoleAcl roleAcl = SysRoleAcl.builder().roleId(roleId)
                    .aclId(aclId).operator(RequestHolder.getCurrentUser().getUsername())
                    //IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())
                    .operateIp("127.0.0.1").operateTime(new Date()).build();
            roleAclList.add(roleAcl);
        }
        //批量新增
        sysRoleAclMapper.batchInsert(roleAclList);
        saveRoleAclLog(roleId,null,aclIdList);
    }

    private void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_ACL);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
//        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        sysLogService.add(sysLog);
    }

}
