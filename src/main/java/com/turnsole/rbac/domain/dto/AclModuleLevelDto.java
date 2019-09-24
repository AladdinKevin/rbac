package com.turnsole.rbac.domain.dto;

import com.google.common.collect.Lists;
import com.turnsole.rbac.domain.model.SysAclmodule;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author:徐凯
 * @date:2019/9/19,18:38
 * @what I say:just look,do not be be
 */
@Getter
@Setter
public class AclModuleLevelDto extends SysAclmodule {

    private List<AclModuleLevelDto> aclModuleLevelList = Lists.newArrayList();
    private List<AclDto> aclLevelList = Lists.newArrayList();
    public static AclModuleLevelDto adapt(SysAclmodule aclmodule){
        AclModuleLevelDto dto = new AclModuleLevelDto();
        //属性复制
        BeanUtils.copyProperties(aclmodule, dto);
        return dto;
    }

}
