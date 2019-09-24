package com.turnsole.rbac.controller;

import com.turnsole.rbac.common.JsonData;
import com.turnsole.rbac.domain.dto.DeptLevelDto;
import com.turnsole.rbac.domain.param.DeptParam;
import com.turnsole.rbac.service.ISysDeptService;
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
 * @date:2019/8/1,17:26
 * @what I say:just look,do not be be
 */
@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {

    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private ISysTreeService deptTreeService;

    @RequestMapping("dept.page")
    public ModelAndView page(){
        return new ModelAndView("dept");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam dept){
        try {
            sysDeptService.save(dept);
            return JsonData.success();
        }catch (Exception e){
            return JsonData.fail(e.getMessage());
        }
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData deptTree(){
        try {
            List<DeptLevelDto> deptLevelDtoList = deptTreeService.deptTree();
            return JsonData.success(deptLevelDtoList);
        }catch (Exception e){
            return JsonData.fail(e.getMessage());
        }
    }


    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam dept){
        try {
            sysDeptService.update(dept);
            return JsonData.success();
        }catch (Exception e){
            return JsonData.fail(e.getMessage());
        }
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData deleteDept(@RequestParam("deptId") int deptId){
        try {
            sysDeptService.deleteDept(deptId);
            return JsonData.success();
        }catch (Exception e){
            e.printStackTrace();
            return JsonData.fail(e.getMessage());
        }
    }

}
