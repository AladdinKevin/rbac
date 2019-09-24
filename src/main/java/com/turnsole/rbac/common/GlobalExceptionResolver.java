package com.turnsole.rbac.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.turnsole.rbac.exception.RBACException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 异常处理逻辑类
 * @author:徐凯
 * @date:2019/7/31,21:44
 * @what I say:just look,do not be be
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionResolver {

    @ExceptionHandler(value = RBACException.class)
    public @ResponseBody String serviceCommonExceptionHandler(RBACException e){
        //对捕获的异常进行处理并打印日志等,之后返回json数据,方式与controller相同
        //log.error();
        try {
            JsonData result = JsonData.fail(e.getMessage());
            return result.toJsonString();
        }catch (JsonProcessingException ex){
            ex.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView exceptionHandler(){
        String defaultMsg = "System error";
        JsonData result = JsonData.fail(defaultMsg);
        return new ModelAndView("error", result.toMap());
    }

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception ex) {
        String url = request.getRequestURL().toString();
        ModelAndView mv;
        String defaultMsg = "System error";

        /**
         * 该项目规范
         * 所有 json请求 以 .json结尾
         * 所有 页面请求 以 .page结尾
         */
        if (url.endsWith(".json")){
            if (ex instanceof RBACException){
                JsonData result = JsonData.fail(ex.getMessage());
                //此处教学中用的是 spring.xml中bean配置的方式,class 是MappingJackson2JsonView
                //笔者不想再配置spring.xml 因此直接给出了一个jsonView的 页面
                mv = new ModelAndView("jsonView", result.toMap());
            }else {
                log.error("unknow json exception, url:"+ url, ex);
                JsonData result = JsonData.fail(defaultMsg);
                mv = new ModelAndView("jsonView", result.toMap());
            }
        }else if (url.endsWith(".page")){
            log.error("unknow page exception, url:"+ url, ex);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("error", result.toMap());
        }else {
            log.error("unknow exception, url:"+ url, ex);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("jsonView", result.toMap());
        }
        return mv;
    }
}
