package com.turnsole.rbac.service.impl;

import com.google.common.collect.Lists;
import com.turnsole.rbac.common.RequestHolder;
import com.turnsole.rbac.domain.beans.CacheKeyConstants;
import com.turnsole.rbac.domain.model.SysAcl;
import com.turnsole.rbac.mapper.SysAclMapper;
import com.turnsole.rbac.mapper.SysRoleAclMapper;
import com.turnsole.rbac.mapper.SysRoleUserMapper;
import com.turnsole.rbac.service.ISysCoreService;
import com.turnsole.rbac.service.SysCacheService;
import com.turnsole.rbac.util.JsonMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** 专门用来获取权限点和权限树的service
 * @author:徐凯
 * @date:2019/9/21,19:45
 * @what I say:just look,do not be be
 */
@Service
public class SysCoreServiceImpl implements ISysCoreService {

    @Resource
    private SysAclMapper sysAclMapper;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    private SysCacheService sysCacheService;

    //获取当前用户的权限列表
    @Override
    public List<SysAcl> getCurrentUserAclList() {
        int userId = RequestHolder.getCurrentUser().getId();
        //获取当前用户的权限
        return this.getUserAclList(userId);
    }

    //获取角色上分配的权限列表
    @Override
    public List<SysAcl> getRoleAclList(int roleId) {
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclIdList)){
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(aclIdList);
    }

    @Override
    public List<SysAcl> getUserAclList(int userId) {
        if (isSuperAdmin()){
             return sysAclMapper.getAll();
        }
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)){
            return Lists.newArrayList();
        }
        //获取当前用户权限列表
        List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userAclIdList)){
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(userAclIdList);
    }

    @Override
    public boolean hasUrlAcl(String url) {
        if (isSuperAdmin()){
            return true;
        }
        List<SysAcl> aclList = sysAclMapper.getByUrl(url);
        if (CollectionUtils.isEmpty(aclList)){
            return true;
        }

        List<SysAcl> userAclList = this.getCurrentUserAclListFromCache();

        Set<Integer> userAclIdSet = userAclList.stream().map(userAcl -> userAcl.getId()).collect(Collectors.toSet());
        boolean hasValidAcl = false;
        //规则:只要有一个权限点有权限,那么我们就任务有访问权限
        for (SysAcl acl : aclList){
            // 判断用户是否具有某个权限点的访问权限
            if (acl == null || acl.getStatus() != 1){//权限点无效
                continue;
            }
            hasValidAcl = true;
            if (userAclIdSet.contains(acl.getId())){
                return true;
            }
        }
        if (!hasValidAcl){
            return true;
        }
        return false;
    }

    //判断是否超级管理员
    public boolean isSuperAdmin() {
        //规则自己制定
        //比如,用户名为admin或者邮箱中保存 admin字样
        //比如指定某个用户,比如指定某个权限
        //最好是通过配置文件获取
        return true;
    }

    /**
     * 先缓存中取
     * @return
     */
    public List<SysAcl> getCurrentUserAclListFromCache(){
        int userId = RequestHolder.getCurrentUser().getId();
        String cacheValue = sysCacheService.getFromCache(CacheKeyConstants.USER_ACLS, String.valueOf(userId));
        if (StringUtils.isBlank(cacheValue)){
            List<SysAcl> aclList = this.getCurrentUserAclList();
            if (CollectionUtils.isNotEmpty(aclList)){
                sysCacheService.saveCache(JsonMapper.obj2String(aclList),600,CacheKeyConstants.USER_ACLS,
                        String.valueOf(userId));
            }
            return aclList;
        }
        return JsonMapper.string2Obj(cacheValue, new TypeReference() {});
    }
}
