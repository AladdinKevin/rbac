package com.turnsole.rbac.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.turnsole.rbac.common.JsonData;
import com.turnsole.rbac.domain.model.SysUser;
import com.turnsole.rbac.domain.param.SysRoleParam;
import com.turnsole.rbac.service.*;
import com.turnsole.rbac.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author:徐凯
 * @date:2019/9/20,16:07
 * @what I say:just look,do not be be
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysTreeService sysTreeService;
    @Autowired
    private ISysRoleAclService sysRoleAclService;
    @Autowired
    private ISysRoleUserService sysRoleUserService;
    @Autowired
    private ISysUserService sysUserService;

    @RequestMapping("role.page")
    public ModelAndView page() {
        return new ModelAndView("role");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData save(SysRoleParam param) {
        sysRoleService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData update(SysRoleParam param) {
        sysRoleService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData list() {
        return JsonData.success(sysRoleService.getAll());
    }

    /**
     * 某个角色拥有的全部权限
     */
    @RequestMapping("/roleTree.json")
    @ResponseBody
    public JsonData roleTree(@RequestParam("roleId") int roleId) {
        return JsonData.success(sysTreeService.roleTree(roleId));
    }

    /**
     * @param roleId
     * @param aclIds 非必填,默认空字符串
     * @return
     */
    @RequestMapping("/changeAcls.json")
    @ResponseBody
    public JsonData changeAcls(@RequestParam("roleId") int roleId, @RequestParam(value = "aclIds", required = false,defaultValue = "") String aclIds) {
        List<Integer> aclIdList = StringUtil.slitTOlistInt(aclIds);
        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return JsonData.success();
    }

    @RequestMapping("/changeUsers.json")
    @ResponseBody
    public JsonData changeUsers(@RequestParam("roleId") int roleId, @RequestParam(value = "userIds", required = false,defaultValue = "") String userIds) {
        List<Integer> userIdList = StringUtil.slitTOlistInt(userIds);
        sysRoleUserService.changeRoleUsers(roleId, userIdList);
        return JsonData.success();
    }

    /**
     * 当前该角色分配的全部用户
     * @param roleId
     * @return
     */
    @RequestMapping("/users.json")
    @ResponseBody
    public JsonData users(@RequestParam("roleId") int roleId){
        //获取选中的用户列表
        List<SysUser> selectedUserList = sysRoleUserService.getuserListByRoleId(roleId);
        //获取全部的用户列表
        List<SysUser> allUsersList = sysUserService.getAll();
        //获取未选中的用户列表
        List<SysUser> unSelectedUsersList = Lists.newArrayList();
        Set<Integer> selectedUserIdSet = selectedUserList.stream().map(sysUser -> sysUser.getId()).collect(Collectors.toSet());
        for (SysUser user : allUsersList){
            //当前用户状态可使用，并且是不包含在已选中用户列表中
            if (user.getStatus() == 1 && !selectedUserIdSet.contains(user.getId())){
                unSelectedUsersList.add(user);
            }
        }
        Map<String,List<SysUser>> map = Maps.newHashMap();
        map.put("selected",selectedUserList);
        map.put("unselected",unSelectedUsersList);
        return JsonData.success(map);
    }

}
