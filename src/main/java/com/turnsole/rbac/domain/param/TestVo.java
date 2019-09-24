package com.turnsole.rbac.domain.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author:徐凯
 * @date:2019/7/31,23:38
 * @what I say:just look,do not be be
 */
@Setter
@Getter
public class TestVo {
    @NotBlank
    private String msg;
    @NotNull
    private Integer id;
}
