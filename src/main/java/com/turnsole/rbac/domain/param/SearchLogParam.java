package com.turnsole.rbac.domain.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:徐凯
 * @date:2019/9/23,17:27
 * @what I say:just look,do not be be
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchLogParam {

    private Integer type;
    private String beforeSeq;
    private String afterSeq;
    private String operator;
    //yyyy-MM-dd HH:mm:ss
    private String fromTime;
    //yyyy-MM-dd HH:mm:ss
    private String toTime;
}
