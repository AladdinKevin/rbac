package com.turnsole.rbac.common;

import com.turnsole.rbac.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Intercepts;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 拦截http请求 做处理操作
 * 请求前
 * 请求结束
 * 请求异常
 * 可做请求耗时统计
 *
 * @author:徐凯
 * @date:2019/8/1,16:50
 * @what I say:just look,do not be be
 */
@Slf4j
@Component
public class HttpInterceptor extends HandlerInterceptorAdapter {
    private static final String START_TIME = "requestStartTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI().toString();
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.info("request finished url:{}, params:{}", url, JsonMapper.obj2String(parameterMap));
        long start = System.currentTimeMillis();
        request.setAttribute(START_TIME, start);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        this.removeThreadLocalInfo();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long start = (Long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        String url = request.getRequestURI().toString();
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.info("request finished url:{}, params:{}, cost:{}", url, JsonMapper.obj2String(parameterMap), (end - start));

        this.removeThreadLocalInfo();
    }

    public void removeThreadLocalInfo() {
        RequestHolder.remove();
    }
}
