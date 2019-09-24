package com.turnsole.rbac.service.impl;

import com.google.common.base.Preconditions;
import com.turnsole.rbac.common.RequestHolder;
import com.turnsole.rbac.domain.beans.PageQuery;
import com.turnsole.rbac.domain.beans.PageResult;
import com.turnsole.rbac.domain.model.SysAcl;
import com.turnsole.rbac.domain.param.AclParam;
import com.turnsole.rbac.exception.ParamException;
import com.turnsole.rbac.mapper.SysAclMapper;
import com.turnsole.rbac.service.ISysAclService;
import com.turnsole.rbac.service.ISysLogService;
import com.turnsole.rbac.util.BeanValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author:徐凯
 * @date:2019/9/20,15:29
 * @what I say:just look,do not be be
 */
@Service
@Slf4j
public class SysAclServiceImpl implements ISysAclService {

    @Resource
    private SysAclMapper sysAclMapper;
    @Autowired
    private ISysLogService sysLogService;

    @Override
    public void save(AclParam param) {
        BeanValidator.check(param);
        if (checkExit(param.getAclmoduleId(),param.getName(),null)){
            throw new ParamException("","当前权限模块下面存在相同名称的权限点");
        }
        SysAcl acl = SysAcl.builder().name(param.getName()).aclmoduleId(param.getAclmoduleId()).
                url(param.getUrl()).type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
        acl.setCode(generateCode());
        acl.setOperator(RequestHolder.getCurrentUser().getUsername());
//        acl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        acl.setOperateTime(new Date());
        sysAclMapper.insert(acl);
        sysLogService.saveAclLog(null,acl);
    }

    @Override
    public void update(AclParam param) {
        BeanValidator.check(param);
        if (checkExit(param.getAclmoduleId(),param.getName(),param.getId())){
            throw new ParamException("","当前权限模块下面存在相同名称的权限点");
        }
        SysAcl before = sysAclMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的权限点不存在");

        SysAcl after = SysAcl.builder().id(param.getId()).name(param.getName()).aclmoduleId(param.getAclmoduleId()).
                url(param.getUrl()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
//        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        sysAclMapper.updateByPrimaryKey(after);
        sysLogService.saveAclLog(before,after);
    }

    @Override
    public PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        int count = sysAclMapper.countByAclModuleId(aclModuleId);
        if (count > 0){
            List<SysAcl> sysAclList = sysAclMapper.getPageByAclModuleId(aclModuleId,pageQuery);
            return PageResult.<SysAcl>builder().data(sysAclList).total(count).build();
        }
        return PageResult.<SysAcl>builder().build();
    }

    private String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int)(Math.random() * 100);
    }

    public boolean checkExit(int aclMoudleId, String name, Integer id) {
        return sysAclMapper.countByNameAndAclModuleId(aclMoudleId,name,id) > 0;
    }
}
