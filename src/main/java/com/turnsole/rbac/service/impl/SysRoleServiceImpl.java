package com.turnsole.rbac.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.turnsole.rbac.common.RequestHolder;
import com.turnsole.rbac.domain.model.SysRole;
import com.turnsole.rbac.domain.model.SysUser;
import com.turnsole.rbac.domain.param.SysRoleParam;
import com.turnsole.rbac.exception.ParamException;
import com.turnsole.rbac.mapper.SysRoleAclMapper;
import com.turnsole.rbac.mapper.SysRoleMapper;
import com.turnsole.rbac.mapper.SysRoleUserMapper;
import com.turnsole.rbac.mapper.SysUserMapper;
import com.turnsole.rbac.service.ISysLogService;
import com.turnsole.rbac.service.ISysRoleService;
import com.turnsole.rbac.util.BeanValidator;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:徐凯
 * @date:2019/9/20,16:08
 * @what I say:just look,do not be be
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Autowired
    private ISysLogService sysLogService;

    @Override
    public void save(SysRoleParam param) {
        BeanValidator.check(param);
        if (checkExit(param.getName(), null)) {
            throw new ParamException("", "角色已经存在");
        }
        SysRole role = SysRole.builder().name(param.getName()).status(param.getStatus()).remark(param.getRemark())
                .type(param.getType()).build();
        role.setOperator(RequestHolder.getCurrentUser().getUsername());
//        role.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        role.setOperateTime(new Date());

        sysRoleMapper.insert(role);
        sysLogService.saveRoleLog(null,role);
    }

    @Override
    @Transactional
    public void update(SysRoleParam param) {
        BeanValidator.check(param);
        if (checkExit(param.getName(), param.getId())) {
            throw new ParamException("", "待更新的角色名称已经存在");
        }
        SysRole before = sysRoleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的角色不存在");

        SysRole after = SysRole.builder().id(param.getId()).name(param.getName()).status(param.getStatus()).remark(param.getRemark())
                .type(param.getType()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
//        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        sysRoleMapper.updateByPrimaryKey(after);
        sysLogService.saveRoleLog(before,after);
    }

    @Override
    public List<SysRole> getAll() {
        return sysRoleMapper.selectAll();
    }

    /**
     * 根据指定用户id获取对应角色
     * @param userId
     * @return
     */
    @Override
    public List<SysRole> getByUserId(int userId) {
        List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)){
            return Lists.newArrayList();
        }
        return sysRoleMapper.getByidList(roleIdList);
    }

    //权限分配的角色
    @Override
    public List<SysRole> getRoleListByAclId(int aclId) {
        List<Integer> roleIdList = sysRoleAclMapper.getRoleIdListByAclId(aclId);
        //根据roleIdList获取sysRole
        return sysRoleMapper.getByidList(roleIdList);
    }

    @Override
    public List<SysUser> getUserListByRoleList(List<SysRole> roleList) {
        if (CollectionUtils.isEmpty(roleList)){
            return Lists.newArrayList();
        }
        List<Integer> roleIdList = roleList.stream().map(role -> role.getId()).collect(Collectors.toList());
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(userIdList)){
            return Lists.newArrayList();
        }
        return sysUserMapper.getByIdList(userIdList);
    }

    private boolean checkExit(String name, Integer id) {
        return sysRoleMapper.countByName(name,id) > 0;
    }

}
