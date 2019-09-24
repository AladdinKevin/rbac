package com.turnsole.rbac.controller;

import com.google.common.collect.Maps;
import com.turnsole.rbac.common.JsonData;
import com.turnsole.rbac.domain.beans.PageQuery;
import com.turnsole.rbac.domain.model.SysRole;
import com.turnsole.rbac.domain.param.AclParam;
import com.turnsole.rbac.service.ISysAclService;
import com.turnsole.rbac.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author:徐凯
 * @date:2019/8/14,9:35
 * @what I say:just look,do not be be
 */
@Controller
@RequestMapping("/sys/acl")
@Slf4j
public class SysAclController {

    @Autowired
    private ISysAclService sysAclService;
    @Autowired
    private ISysRoleService sysRoleService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclParam param) {
        sysAclService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData update(AclParam param) {
        sysAclService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData list(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery) {
        return JsonData.success(sysAclService.getPageByAclModuleId(aclModuleId,pageQuery));
    }

    /**
     * 得到的是
     * 当前哪些角色分配了该权限点
     * 和当前哪些用户拥有这个权限点
     * @param aclId
     * @return
     */
    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@Param("aclId") int aclId) {
        Map<String,Object> map = Maps.newHashMap();
        //该权限分配的用户列表
        List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);
        map.put("roles",roleList);
        map.put("users",sysRoleService.getUserListByRoleList(roleList));
        return JsonData.success(map);
    }

}
