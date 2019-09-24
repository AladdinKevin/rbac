package com.turnsole.rbac.controller;

import com.google.common.collect.Maps;
import com.turnsole.rbac.common.JsonData;
import com.turnsole.rbac.domain.beans.PageQuery;
import com.turnsole.rbac.domain.beans.PageResult;
import com.turnsole.rbac.domain.model.SysUser;
import com.turnsole.rbac.domain.param.UserParam;
import com.turnsole.rbac.service.ISysRoleService;
import com.turnsole.rbac.service.ISysTreeService;
import com.turnsole.rbac.service.ISysUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**用户
 * @author:徐凯
 * @date:2019/8/5,17:44
 * @what I say:just look,do not be be
 */
@RequestMapping("/sys/user")
@Controller
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysTreeService sysTreeService;
    @Autowired
    private ISysRoleService sysRoleService;

    @RequestMapping("noAuth.page")
    public ModelAndView noAuth() {
        return new ModelAndView("noAuth");
    }

    //新增
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveUser(UserParam param){
        sysUserService.save(param);
        return JsonData.success();
    }

    //更新
    @RequestMapping("update.json")
    @ResponseBody
    public JsonData updateUser(UserParam param){
        sysUserService.update(param);
        return JsonData.success();
    }

    //用户分页展示
    @RequestMapping("page.json")
    @ResponseBody
    public JsonData page(@RequestParam("deptId") int deptId, PageQuery pageQuery){
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId,pageQuery);
        return JsonData.success(result);
    }

    //获取当前用户所具有的权限
    @RequestMapping("userAcls.json")
    @ResponseBody
    public JsonData getUserAcls(@Param("userId") int userId){
        Map<String,Object> map = Maps.newHashMap();
        map.put("acls",sysTreeService.userAlcTree(userId));
        map.put("roles",sysRoleService.getByUserId(userId));
        return JsonData.success();
    }

}
