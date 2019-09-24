package com.turnsole.rbac.domain.param;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author:徐凯
 * @date:2019/9/20,16:09
 * @what I say:just look,do not be be
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleParam {

    private Integer id;

    @NotBlank(message = "角色名称不可以为空")
    @Length(min = 2, max = 20, message = "角色名称长度需在2-64个字之间")
    private String name;

    @Min(value = 1, message = "角色类型不合法")
    @Max(value = 2, message = "角色类型不合法")
    private Integer type = 1;

    @NotNull(message = "必须指定角色的状态")
    @Min(value = 0, message = "角色类型不合法")
    @Max(value = 1, message = "角色类型不合法")
    private Integer status;

    @Length(min = 0, max = 200, message = "角色备注长度需要在0到200个字之间")
    private String remark;

}
