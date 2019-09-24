package com.turnsole.rbac.domain.model;

/**
 * @author:徐凯
 * @date:2019/8/1,20:42
 * @what I say:just look,do not be be
 */
public class SysLogWithBLOBs extends SysLog {
    private String oldValue;

    private String newValue;


    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
