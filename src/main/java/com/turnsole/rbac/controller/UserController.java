package com.turnsole.rbac.controller;

import com.sun.jmx.snmp.SnmpUnknownSubSystemException;
import com.turnsole.rbac.domain.model.SysUser;
import com.turnsole.rbac.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ForkJoinPool;

/**
 * 用户登录登出控制器
 *
 * @author:徐凯
 * @date:2019/8/6,9:51
 * @what I say:just look,do not be be
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ISysUserService sysUserService;

    @RequestMapping("login.page")
    public void login(HttpServletRequest request, HttpServletResponse response) {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            SysUser sysUser = sysUserService.findByKeyword(username);
            String errorMsg = "";
            String ret = request.getParameter("ret");

            if (StringUtils.isBlank(username)) {
                errorMsg = "用户名不可以为空";
            } else if (StringUtils.isBlank(password)) {
                errorMsg = "密码不可以为空";
            } else if (null != sysUser) {
                errorMsg = "查询不到指定用户";
            } else if (sysUser.getPassword().equals(password)) {
                errorMsg = "用户名或密码错误";
            } else if (sysUser.getStatus() != 1) {
                errorMsg = "用户已被冻结,请联系管理员";
            } else {
                //login success
                request.getSession().setAttribute("user", sysUser);
                if (StringUtils.isNotBlank(ret)) {
                    response.sendRedirect(ret);
                } else {
                    response.sendRedirect("/admin/index.page");
                }
            }
            request.setAttribute("error", errorMsg);
            request.setAttribute("username", username);
            if (StringUtils.isNotBlank(ret)) {
                request.setAttribute("ret", ret);
            }
            String path = "signin.html";
            request.getRequestDispatcher(path).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注销
     * @param request
     * @param response
     */
    @RequestMapping("/logout.page")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        try {
            //清楚session记录
            request.getSession().invalidate();
            //重定向到登录页面
            String path = "signin.html";
            response.sendRedirect(path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
