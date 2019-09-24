package com.turnsole.rbac.service.impl;

import com.google.common.base.Preconditions;
import com.turnsole.rbac.common.RequestHolder;
import com.turnsole.rbac.domain.beans.LogType;
import com.turnsole.rbac.domain.beans.PageQuery;
import com.turnsole.rbac.domain.beans.PageResult;
import com.turnsole.rbac.domain.dto.SearchLogDto;
import com.turnsole.rbac.domain.model.*;
import com.turnsole.rbac.domain.param.SearchLogParam;
import com.turnsole.rbac.exception.ParamException;
import com.turnsole.rbac.mapper.*;
import com.turnsole.rbac.service.ISysLogService;
import com.turnsole.rbac.service.ISysRoleAclService;
import com.turnsole.rbac.service.ISysRoleUserService;
import com.turnsole.rbac.util.BeanValidator;
import com.turnsole.rbac.util.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author:徐凯
 * @date:2019/9/23,16:53
 * @what I say:just look,do not be be
 */
@Service
public class SysLogServiceImpl implements ISysLogService {

    @Resource
    private SysLogMapper sysLogMapper;
    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysAclmoduleMapper sysAclmoduleMapper;
    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private ISysRoleAclService sysRoleAclService;
    @Resource
    private ISysRoleUserService sysRoleUserService;

    @Override
    public void saveDeptLog(SysDept before, SysDept after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_DEPT);
        sysLog.setTargetId(after == null ? before.getId(): after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
//        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insert(sysLog);
    }

    @Override
    public void saveUserLog(SysUser before, SysUser after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_USER);
        sysLog.setTargetId(after == null ? before.getId(): after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
//        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insert(sysLog);
    }

    @Override
    public void saveAclModuleLog(SysAclmodule before, SysAclmodule after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ACL_MODULE);
        sysLog.setTargetId(after == null ? before.getId(): after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
//        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insert(sysLog);
    }

    @Override
    public void saveAclLog(SysAcl before, SysAcl after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ACL);
        sysLog.setTargetId(after == null ? before.getId(): after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
//        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insert(sysLog);
    }

    @Override
    public void saveRoleLog(SysRole before, SysRole after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE);
        sysLog.setTargetId(after == null ? before.getId(): after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
//        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insert(sysLog);
    }

    /**
     * 分页查询
     * @param param
     * @param pageQuery
     * @return
     */
    @Override
    public PageResult<SysLogWithBLOBs> searchPageList(SearchLogParam param, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        SearchLogDto dto = new SearchLogDto();
        dto.setType(param.getType());
        if (StringUtils.isNotBlank(param.getBeforeSeq())) {
            dto.setBeforeSeq("%" + param.getBeforeSeq() + "%");
        }
        if (StringUtils.isNotBlank(param.getAfterSeq())) {
            dto.setAfterSeq("%" + param.getAfterSeq() + "%");
        }
        if (StringUtils.isNotBlank(param.getOperator())){
            dto.setOperator("%" + param.getOperator() + "%");
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotBlank(param.getFromTime())){
                dto.setFromTime(dateFormat.parse(param.getFromTime()));
            }
            if (StringUtils.isNotBlank(param.getToTime())){
                dto.setToTime(dateFormat.parse(param.getToTime()));
            }
        }catch (Exception e){
            throw new ParamException("","传入的日期格式有问题，正确格式为:yyyy-MM-dd HH:mm:ss");
        }
        int count = sysLogMapper.countBySerchDto(dto);
        if (count > 0){
            List<SysLogWithBLOBs> sysLogList = sysLogMapper.getPageListBySearchDto(dto, pageQuery);
            return PageResult.<SysLogWithBLOBs>builder().total(count).data(sysLogList).build();
        }
        return PageResult.<SysLogWithBLOBs>builder().build();
    }

    @Override
    public void recover(int id) {
        SysLogWithBLOBs syslog = sysLogMapper.selectByPrimaryKey(id);
        Preconditions.checkNotNull(syslog,"待还原的记录不存在");
        switch(syslog.getType()){
            case LogType.TYPE_DEPT:
                SysDept before = sysDeptMapper.selectByPrimaryKey(syslog.getTargetId());
                Preconditions.checkNotNull(before,"要还原的部门已经不存在了");
                if (StringUtils.isBlank(syslog.getNewValue()) || StringUtils.isBlank(syslog.getOldValue())){
                    throw new ParamException("","新增操作和删除操作不做还原");
                }
                SysDept after = JsonMapper.string2Obj(syslog.getOldValue(), new TypeReference<SysDept>() {});
                after.setOperator(RequestHolder.getCurrentUser().getUsername());
//                after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                after.setOperateTime(new Date());
                sysDeptMapper.updateByPrimaryKey(after);
                saveDeptLog(before,after);
                break;
            case LogType.TYPE_USER:
                SysUser userBefore = sysUserMapper.selectByPrimaryKey(syslog.getTargetId());
                Preconditions.checkNotNull(userBefore,"要还原的用户已经不存在了");
                if (StringUtils.isBlank(syslog.getNewValue()) || StringUtils.isBlank(syslog.getOldValue())){
                    throw new ParamException("","新增操作和删除操作不做还原");
                }
                SysUser userAfter = JsonMapper.string2Obj(syslog.getOldValue(), new TypeReference<SysUser>() {});
                userAfter.setOperator(RequestHolder.getCurrentUser().getUsername());
//                userAfter.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                userAfter.setOperateTime(new Date());
                sysUserMapper.updateByPrimaryKey(userAfter);
                saveUserLog(userBefore,userAfter);
                break;
            case LogType.TYPE_ACL_MODULE:
                SysAclmodule aclmoduleBefore = sysAclmoduleMapper.selectByPrimaryKey(syslog.getTargetId());
                Preconditions.checkNotNull(aclmoduleBefore,"要还原的权限模块已经不存在了");
                if (StringUtils.isBlank(syslog.getNewValue()) || StringUtils.isBlank(syslog.getOldValue())){
                    throw new ParamException("","新增操作和删除操作不做还原");
                }
                SysAclmodule aclmoduleAfter = JsonMapper.string2Obj(syslog.getOldValue(), new TypeReference<SysAclmodule>() {});
                aclmoduleAfter.setOperator(RequestHolder.getCurrentUser().getUsername());
//                aclmoduleAfter.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                aclmoduleAfter.setOperateTime(new Date());
                sysAclmoduleMapper.updateByPrimaryKey(aclmoduleAfter);
                saveAclModuleLog(aclmoduleBefore,aclmoduleAfter);
                break;
            case LogType.TYPE_ACL:
                SysAcl aclBefore = sysAclMapper.selectByPrimaryKey(syslog.getTargetId());
                Preconditions.checkNotNull(aclBefore,"要还原的权限点已经不存在了");
                if (StringUtils.isBlank(syslog.getNewValue()) || StringUtils.isBlank(syslog.getOldValue())){
                    throw new ParamException("","新增操作和删除操作不做还原");
                }
                SysAcl aclAfter = JsonMapper.string2Obj(syslog.getOldValue(), new TypeReference<SysAcl>() {});
                aclAfter.setOperator(RequestHolder.getCurrentUser().getUsername());
//                aclAfter.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                aclAfter.setOperateTime(new Date());
                sysAclMapper.updateByPrimaryKey(aclAfter);
                saveAclLog(aclBefore,aclAfter);
                break;
            case LogType.TYPE_ROLE:
                SysRole roleBefore = sysRoleMapper.selectByPrimaryKey(syslog.getTargetId());
                Preconditions.checkNotNull(roleBefore,"要还原的角色已经不存在了");
                if (StringUtils.isBlank(syslog.getNewValue()) || StringUtils.isBlank(syslog.getOldValue())){
                    throw new ParamException("","新增操作和删除操作不做还原");
                }
                SysRole roleAfter = JsonMapper.string2Obj(syslog.getOldValue(), new TypeReference<SysRole>() {
                });
                roleAfter.setOperator(RequestHolder.getCurrentUser().getUsername());
//                roleAfter.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                roleAfter.setOperateTime(new Date());
                sysRoleMapper.updateByPrimaryKey(roleAfter);
                saveRoleLog(roleBefore,roleAfter);
                break;
            case LogType.TYPE_ROLE_ACL:
                SysRole role = sysRoleMapper.selectByPrimaryKey(syslog.getTargetId());
                Preconditions.checkNotNull(role,"要还原的角色已经不存在了");
                sysRoleAclService.changeRoleAcls(syslog.getTargetId(),JsonMapper.string2Obj(syslog.getOldValue(), new TypeReference<SysRoleAcl>() {
                }));
                break;
            case LogType.TYPE_ROLE_USER:
                SysRole role7 = sysRoleMapper.selectByPrimaryKey(syslog.getTargetId());
                Preconditions.checkNotNull(role7,"要还原的角色已经不存在了");
                sysRoleUserService.changeRoleUsers(syslog.getTargetId(),JsonMapper.string2Obj(syslog.getOldValue(), new TypeReference<SysRoleUser>() {
                }));
                break;
            default:;
        }
    }

    @Override
    public void add(SysLogWithBLOBs sysLog) {
        sysLogMapper.insert(sysLog);
    }

}
