package com.turnsole.rbac.service.impl;

import com.google.common.base.Preconditions;
import com.turnsole.rbac.common.RequestHolder;
import com.turnsole.rbac.domain.model.SysAclmodule;
import com.turnsole.rbac.domain.param.AclModuleParam;
import com.turnsole.rbac.exception.ParamException;
import com.turnsole.rbac.mapper.SysAclMapper;
import com.turnsole.rbac.mapper.SysAclmoduleMapper;
import com.turnsole.rbac.service.ISysAclModuleService;
import com.turnsole.rbac.service.ISysLogService;
import com.turnsole.rbac.util.BeanValidator;
import com.turnsole.rbac.util.LevelUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author:徐凯
 * @date:2019/9/19,15:18
 * @what I say:just look,do not be be
 */
@Service
public class SysAclModuleServiceImpl implements ISysAclModuleService {

    @Resource
    private SysAclmoduleMapper aclmoduleMapper;
    @Resource
    private SysAclMapper sysAclMapper;
    @Autowired
    private ISysLogService sysLogService;

    @Override
    public void save(AclModuleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            //存在
            throw new ParamException("", "同一层级下存在相同名称的权限模块");
        }
        SysAclmodule aclmodule = SysAclmodule.builder().name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).status(param.getStatus()).remark(param.getRemark())
                .build();
        aclmodule.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        aclmodule.setOperator(RequestHolder.getCurrentUser().getUsername());
//        aclmodule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        aclmodule.setOperateTime(new Date());
        aclmoduleMapper.insert(aclmodule);
        sysLogService.saveAclModuleLog(null,aclmodule);
    }

    @Override
    public void update(AclModuleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            //存在
            throw new ParamException("", "同一层级下存在相同名称的权限模块");
        }
        SysAclmodule before = aclmoduleMapper.selectByPrimaryKey(param.getId());
        //检查这个对象是否为空,为空抛出异常
        Preconditions.checkNotNull(before, "待更新的权限模块不存在");

        SysAclmodule after = SysAclmodule.builder().name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).status(param.getStatus()).remark(param.getRemark())
                .id(param.getId()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
//        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        updateWithChild(before, after);
        sysLogService.saveAclModuleLog(before,after);

    }

    //删除权限模块
    @Override
    public void deleteAclModule(int id) {
        SysAclmodule aclmodule = aclmoduleMapper.selectByPrimaryKey(id);
        //欲删除权限模块不存在无法删除
        Preconditions.checkNotNull(aclmodule,"待删除权限模块不存在,无法删除");
        //权限模块下有子模块无法删除
        if (aclmoduleMapper.countByParentId(id) > 0){
            throw new ParamException("","当前模块存在子模块,无法删除");
        }
        if (sysAclMapper.countByAclModuleId(id) > 0) {
            throw new ParamException("","当前权限模块下有权限点,无法删除");
        }
        aclmoduleMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void updateWithChild(SysAclmodule before, SysAclmodule after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            List<SysAclmodule> aclmoduleList = aclmoduleMapper.getChildDeptListByLevel(before.getLevel());
            if (CollectionUtils.isNotEmpty(aclmoduleList)) {
                for (SysAclmodule aclmodule : aclmoduleList) {
                    String level = aclmodule.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        aclmodule.setLevel(level);
                    }
                }
                aclmoduleMapper.batchUpdateLevel(aclmoduleList);
            }
        }
        aclmoduleMapper.updateByPrimaryKey(after);
    }

    /**
     * 校验权限模块名称是否重复
     *
     * @return
     */
    private boolean checkExist(Integer parentId, String aclModuleName, Integer aclModuleId) {
        return aclmoduleMapper.countByNameAndParentId(parentId, aclModuleName, aclModuleId) > 0;
    }

    private String getLevel(Integer aclModuleId) {
        SysAclmodule aclmodule = aclmoduleMapper.selectByPrimaryKey(aclModuleId);
        if (aclmodule == null) {
            return null;
        }
        return aclmodule.getLevel();
    }


}
