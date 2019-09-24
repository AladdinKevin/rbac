package com.turnsole.rbac.util;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:徐凯
 * @date:2019/9/23,10:11
 * @what I say:just look,do not be be
 */
public class StringUtil {

    public static List<Integer> slitTOlistInt(String str){
        List<String> stringList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
        List<Integer> list = stringList.stream().map(strItem -> Integer.parseInt(strItem)).collect(Collectors.toList());
        return list;
    }
}
