package com.turnsole.rbac.domain.model;

import lombok.Data;

import java.util.Date;
@Data
public class SysLog {
    private Integer id;

    private Integer type;

    private Integer targetId;

    private Integer status;

    private String operator;

    private Date operateTime;

    private String operateIp;

}