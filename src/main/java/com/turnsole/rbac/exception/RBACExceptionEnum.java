package com.turnsole.rbac.exception;

/**
 * @author:徐凯
 * @date:2019/7/31,21:53
 * @what I say:just look,do not be be
 */
public enum RBACExceptionEnum {
    //未知异常
    UNKNOW_EXCEPTION("RBAC001","未知异常","warn"),
    //SQL异常
    SQL_EXCEPTION("RBAC002","SQL异常","warn")
    ;
    private String errorCode;
    private String errorMsg;
    private String errorType;

    RBACExceptionEnum(String errorCode, String errorMsg, String errorType){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorType = errorType;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
}
