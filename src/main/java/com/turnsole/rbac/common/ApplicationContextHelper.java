package com.turnsole.rbac.common;

import com.google.errorprone.annotations.concurrent.LazyInit;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/** 获取上下文
 *  类不允许懒加载
 * @author:徐凯
 * @date:2019/8/1,16:22
 * @what I say:just look,do not be be
 */
@Component("applicationContextHelper")
@Lazy(value = false)
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        applicationContext = ac;
    }

    public static <T> T popBean(Class<T> clazz){
        if (applicationContext == null){
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    public static <T> T popBean(String name, Class<T> clazz){
        if (applicationContext == null){
            return null;
        }
        return applicationContext.getBean(name, clazz);
    }

}
