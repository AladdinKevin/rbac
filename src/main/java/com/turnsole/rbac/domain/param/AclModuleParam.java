package com.turnsole.rbac.domain.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author:徐凯
 * @date:2019/8/14,9:47
 * @what I say:just look,do not be be
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AclModuleParam {

    private Integer id;
    @NotBlank(message = "权限模块名称不可以为空")
    @Length(min = 2,max = 20,message = "权限名称在2-64个字之间")
    private String name;

    private Integer parentId = 0;

    @NotNull(message = "权限模块状态不可以为空")
    @Min(value = 0,message = "权限模块状态值不合法")
    @Max(value = 1,message = "权限模块状态值不合法")
    private Integer status;

    @NotNull(message = "权限模块展示顺序不可以为空")
    private Integer seq;

    @Length(max = 200, message = "权限模块的备注需要在64个字以内")
    private String remark;

}
