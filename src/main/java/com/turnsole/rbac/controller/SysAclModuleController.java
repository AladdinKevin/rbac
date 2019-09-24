package com.turnsole.rbac.controller;

import com.turnsole.rbac.common.JsonData;
import com.turnsole.rbac.domain.dto.AclModuleLevelDto;
import com.turnsole.rbac.domain.param.AclModuleParam;
import com.turnsole.rbac.service.ISysAclModuleService;
import com.turnsole.rbac.service.ISysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author:徐凯
 * @date:2019/8/14,9:35
 * @what I say:just look,do not be be
 */
@Controller
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController {

    @Autowired
    private ISysAclModuleService sysAclModuleService;
    @Autowired
    private ISysTreeService sysTreeService;

    @RequestMapping("/acl.page")
    public ModelAndView page(){
        return new ModelAndView("acl");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(AclModuleParam param){
        try {
            sysAclModuleService.save(param);
            return JsonData.success();
        }catch (Exception e){
            return JsonData.fail(e.getMessage());
        }
    }

    @RequestMapping("update.json")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam param){
        try {
            sysAclModuleService.update(param);
            return JsonData.success();
        }catch (Exception e){
            return JsonData.fail(e.getMessage());
        }
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        try {
            List<AclModuleLevelDto> dtoList = sysTreeService.aclModuleTree();
            return JsonData.success(dtoList);
        }catch (Exception e){
            return JsonData.fail(e.getMessage());
        }
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData deleteAcl(@RequestParam("id") int id){
        try {
            sysAclModuleService.deleteAclModule(id);
            return JsonData.success();
        }catch (Exception e){
            return JsonData.fail(e.getMessage());
        }
    }

}
