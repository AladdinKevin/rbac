 package com.turnsole.rbac.common;

import com.turnsole.rbac.domain.model.SysUser;

import javax.servlet.http.HttpServletRequest;

/** threadlocal
 * @author:徐凯
 * @date:2019/9/18,22:07
 * @what I say:just look,do not be be
 */
public class RequestHolder {

    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<>();

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    public static void add(SysUser sysUser) {
        userHolder.set(sysUser);
    }

    public static void add(HttpServletRequest request){
        requestHolder.set(request);
    }

    public static SysUser getCurrentUser() {
        return userHolder.get();
    }

    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    public static void remove() {
        userHolder.remove();
        requestHolder.remove();
    }

}
