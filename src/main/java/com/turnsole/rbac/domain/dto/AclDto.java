package com.turnsole.rbac.domain.dto;

import com.turnsole.rbac.domain.model.SysAcl;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author:徐凯
 * @date:2019/9/21,19:38
 * @what I say:just look,do not be be
 */
@Data
public class AclDto extends SysAcl {

    //是否要默认选中
    private boolean checked = false;

    //是否有权限操作
    private boolean hasAcl = false;

    public static AclDto adapt(SysAcl acl) {
        AclDto dto = new AclDto();
        BeanUtils.copyProperties(acl,dto);
        return dto;
    }

}
