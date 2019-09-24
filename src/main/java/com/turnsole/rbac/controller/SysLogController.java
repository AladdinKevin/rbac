package com.turnsole.rbac.controller;

import com.turnsole.rbac.common.JsonData;
import com.turnsole.rbac.domain.beans.PageQuery;
import com.turnsole.rbac.domain.param.SearchLogParam;
import com.turnsole.rbac.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**日志操作
 * @author:徐凯
 * @date:2019/9/23,16:52
 * @what I say:just look,do not be be
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private ISysLogService sysLogService;

    @RequestMapping("/log.page")
    public ModelAndView page() {
        return new ModelAndView("log");
    }

    @RequestMapping("/recover.json")
    @ResponseBody
    public JsonData recover(@RequestParam("id") int id){
        sysLogService.recover(id);
        return JsonData.success();
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(SearchLogParam param, PageQuery pageQuery) {
        return JsonData.success(sysLogService.searchPageList(param,pageQuery));
    }

}
