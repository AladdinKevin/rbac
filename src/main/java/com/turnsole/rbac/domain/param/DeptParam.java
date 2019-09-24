package com.turnsole.rbac.domain.param;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** 部门需要传入的参数
 * @author:徐凯
 * @date:2019/8/1,17:19
 * @what I say:just look,do not be be
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeptParam {

    private Integer id;

    @NotBlank(message = "部门名称不可以为空")
    @Length(max = 15, min = 2, message = "部门名称长度限制在2~15个字符")
    private String name;

    private Integer parentId = 0;

    @NotNull(message = "展示顺序不可以为空")
    private Integer seq;

    @Length(max = 150, message = "备注长度不能超过150个字")
    private String remark;
}
