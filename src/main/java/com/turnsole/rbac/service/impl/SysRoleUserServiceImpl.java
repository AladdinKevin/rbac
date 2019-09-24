package com.turnsole.rbac.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.turnsole.rbac.common.RequestHolder;
import com.turnsole.rbac.domain.beans.LogType;
import com.turnsole.rbac.domain.model.SysLogWithBLOBs;
import com.turnsole.rbac.domain.model.SysRoleUser;
import com.turnsole.rbac.domain.model.SysUser;
import com.turnsole.rbac.mapper.SysRoleUserMapper;
import com.turnsole.rbac.mapper.SysUserMapper;
import com.turnsole.rbac.service.ISysLogService;
import com.turnsole.rbac.service.ISysRoleUserService;
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
 * @date:2019/9/23,10:28
 * @what I say:just look,do not be be
 */
@Service
public class SysRoleUserServiceImpl implements ISysRoleUserService {

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Autowired
    private ISysLogService sysLogService;

    //取出传入角色id 对应的用户列表
    @Override
    public List<SysUser> getuserListByRoleId(int roleId) {
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)){
            return Lists.newArrayList();
        }
        return sysUserMapper.getByIdList(userIdList);
    }

    @Override
    public void changeRoleUsers(int roleId, List<Integer> userIdList) {
        //判断是否一致
        //根据roleId获取对应的userList
        List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (originUserIdList.size() == userIdList.size()){
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            Set<Integer> paramUserIdSet = Sets.newHashSet(userIdList);
            originUserIdSet.removeAll(paramUserIdSet);
            if (CollectionUtils.isEmpty(originUserIdSet)){
                return;
            }
        }
        //更新角色对应的用户
        updateRoleUsers(roleId,userIdList);
        saveRoleUserLog(roleId,originUserIdList,userIdList);
    }


    //更新修改的角色对应的用户
    @Transactional
    public void updateRoleUsers(int roleId, List<Integer> userIdList) {
        //先删除历史记录
        sysRoleUserMapper.deleteByRoleId(roleId);
        //如果传入用户id为空不做处理
        if (CollectionUtils.isEmpty(userIdList)){
            return;
        }
        List<SysRoleUser> roleUserList = Lists.newArrayList();
        for (Integer userId : userIdList){
            SysRoleUser roleUser = SysRoleUser.builder().roleId(roleId).userId(userId)
                    .operator(RequestHolder.getCurrentUser().getUsername())
                    //IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())
                    .operateIp("127.0.0.1")
                    .operateTime(new Date()).build();
            roleUserList.add(roleUser);
        }
        //批量插入
        sysRoleUserMapper.batchInsert(roleUserList);
        saveRoleUserLog(roleId,null,userIdList);
    }

    private void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_USER);
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
