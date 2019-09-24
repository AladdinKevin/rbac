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
 * @date:2019/9/20,15:20
 * @what I say:just look,do not be be
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AclParam {

    private Integer id;
    @NotBlank(message = "权限点名称不可以为空")
    @Length(min = 2, max = 20, message = "权限点名称长度需在2-64个字之间")
    private String name;

    @NotNull(message = "必须指定权限模块")
    private Integer aclmoduleId;

    @Length(min = 6, max = 100, message = "权限点URL长度需在6-256个字之间")
    private String url;

    @NotNull(message = "必须指定权限点的类型")
    @Min(value = 1, message = "权限点类型不合法")
    @Max(value = 3, message = "权限点类型不合法")
    private Integer type;

    @NotNull(message = "必须指定权限点的状态")
    @Min(value = 0, message = "权限点类型不合法")
    @Max(value = 1, message = "权限点类型不合法")
    private Integer status;

    @NotNull(message = "必须指定权限点的展示顺序")
    private Integer seq;

    @Length(min = 0, max = 200, message = "权限点备注长度需要在0到200个字之间")
    private String remark;
}
