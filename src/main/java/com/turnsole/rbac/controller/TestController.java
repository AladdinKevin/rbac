package com.turnsole.rbac.controller;

import com.turnsole.rbac.common.ApplicationContextHelper;
import com.turnsole.rbac.common.JsonData;
import com.turnsole.rbac.domain.model.SysAclmodule;
import com.turnsole.rbac.mapper.SysAclmoduleMapper;
import com.turnsole.rbac.domain.param.TestVo;
import com.turnsole.rbac.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:徐凯
 * @date:2019/7/30,23:07
 * @what I say:just look,do not be be
 */
@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData hello(){
      log.info("hello");
      //throw new RBACException(RBACExceptionEnum.UNKNOW_EXCEPTION);
      //throw new RuntimeException("text exception");
      return JsonData.success("hello .json");
    }

    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVo vo){
        log.error("validate");
        SysAclmoduleMapper aclmoduleMapper = ApplicationContextHelper.popBean(SysAclmoduleMapper.class);
        SysAclmodule sysAclmodule = aclmoduleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.obj2String(sysAclmodule));
        return JsonData.success("test validate");
    }
}
