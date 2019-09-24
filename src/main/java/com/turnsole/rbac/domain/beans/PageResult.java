package com.turnsole.rbac.domain.beans;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author:徐凯
 * @date:2019/8/7,9:30
 * @what I say:just look,do not be be
 */
@Getter
@Setter
@Builder
@ToString
public class PageResult<T> {
    private List<T> data = Lists.newArrayList();
    private int total = 0;
}
