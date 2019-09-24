package com.turnsole.rbac.exception;

import org.apache.ibatis.executor.ErrorContext;

/**
 * @author:徐凯
 * @date:2019/8/1,17:31
 * @what I say:just look,do not be be
 */
public class ParamException extends RuntimeException  {
    private static final long serialVersionUID = 6958499248468627021L;
    /**错误码*/
    private String errorCode;
    /**错误上下文*/
    private ErrorContext errorContext;

    public ParamException(String errorCode, String errorMsg){
        super(errorMsg);
        this.errorCode = errorCode;
    }

    public ParamException(RBACExceptionEnum rbacExceptionEnum){
        super(rbacExceptionEnum.getErrorMsg());
        this.errorCode = rbacExceptionEnum.getErrorCode();
    }

    public ParamException(String errorCode, String errorMsg, Throwable throwable){
        super(errorMsg, throwable);
        this.errorCode = errorCode;
    }

    public ParamException(RBACExceptionEnum rbacExceptionEnum, Throwable throwable){
        super(rbacExceptionEnum.getErrorMsg(), throwable);
        this.errorCode = rbacExceptionEnum.getErrorCode();
    }
}
