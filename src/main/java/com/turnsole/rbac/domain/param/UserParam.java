package com.turnsole.rbac.domain.param;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author:徐凯
 * @date:2019/8/5,17:44
 * @what I say:just look,do not be be
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserParam {

    private Integer id;
    @NotBlank(message = "用户名不可以为空")
    @Length(min = 0,max = 20,message = "用户名长度在20个字以内")
    private String username;

    @NotBlank(message = "手机号不可以为空")
    @Length(min = 1,max = 13,message = "手机号长度在13个字以内")
    private String telephone;

    @NotBlank(message = "邮箱不可以为空")
    @Length(min = 5,max = 50,message = "邮箱长度在50个字以内")
    private String mail;

    @NotNull(message = "必须提供用户所在的部门")
    private Integer deptId;

    @NotNull(message = "必须提供用户状态")
    @Min(value = 0, message = "用户状态不合法")
    @Max(value = 2, message = "用户状态不合法")
    private Integer status;

    @Length(min = 0, max = 200, message = "备注长度需要在200个字以内")
    private String remark;
}
