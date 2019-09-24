package com.turnsole.rbac.util;

import jdk.nashorn.internal.ir.ReturnNode;
import org.apache.commons.lang3.StringUtils;

/** 当前项目业务工具类
 * @author:徐凯
 * @date:2019/8/1,17:34
 * @what I say:just look,do not be be
 */
public class LevelUtil {

    public final static String SEPARATOR = ".";

    public final static String ROOT = "0";

    public static String  calculateLevel(String parentLevel, int parentId){
        if (StringUtils.isBlank(parentLevel)){
            //说明是最顶级
            return ROOT;
        }
        return StringUtils.join(parentLevel, SEPARATOR, parentId);
    }
}
