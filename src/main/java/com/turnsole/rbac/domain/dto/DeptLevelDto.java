package com.turnsole.rbac.domain.dto;

import com.google.common.collect.Lists;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.turnsole.rbac.domain.model.SysDept;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/** 适配用类
 * @author:徐凯
 * @date:2019/8/1,18:16
 * @what I say:just look,do not be be
 */
@Setter
@Getter
@ToString
public class DeptLevelDto extends SysDept {
    private List<DeptLevelDto> deptList = Lists.newArrayList();

    public static DeptLevelDto adapt(SysDept dept){
        DeptLevelDto dto = new DeptLevelDto();
        BeanUtils.copyProperties(dept, dto);
        return dto;
    }
}
