package com.turnsole.rbac.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * @author:徐凯
 * 本工具类主要用作参数的验证.一些必传参数的非空之类的验证
 * 借用Validator 这个工具做的拓展
 * @date:2019/7/31,23:19
 * @what I say:just look,do not be be
 */
public class BeanValidator {

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public static <T>Map<String, String> validate(T t, Class... groups){
        Validator validator = validatorFactory.getValidator();
        Set validateResult = validator.validate(t, groups);
        if (validateResult.isEmpty()){
            return Collections.emptyMap();
        }
        LinkedHashMap errors = Maps.newLinkedHashMap();
        Iterator iterator = validateResult.iterator();
        while (iterator.hasNext()){
            ConstraintViolation violation = (ConstraintViolation) iterator.next();
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return errors;
    }

    public static Map<String, String> validateList(Collection<?> collection){
        Preconditions.checkNotNull(collection);
        Iterator iterator = collection.iterator();
        Map errors;

        do {
            if (!iterator.hasNext()){
                return Collections.emptyMap();
            }
            Object object = iterator.next();
            errors = validate(object, new Class[0]);
        }while (errors.isEmpty());
        return errors;
    }

    public static Map<String, String> check(Object first, Object... objects){
        if (objects != null && objects.length > 0){
           return validateList(Lists.asList(first, objects));
        }else {
            return validate(first, new Class[0]);
        }
    }
}
