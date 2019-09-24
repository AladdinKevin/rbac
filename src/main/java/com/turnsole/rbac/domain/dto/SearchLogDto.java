package com.turnsole.rbac.domain.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author:徐凯
 * @date:2019/9/23,17:29
 * @what I say:just look,do not be be
 */
@Data
public class SearchLogDto {
    //logType
    private Integer type;
    private String beforeSeq;
    private String afterSeq;
    private String operator;
    //yyyy-MM-dd HH:mm:ss
    private Date fromTime;
    //yyyy-MM-dd HH:mm:ss
    private Date toTime;

}
