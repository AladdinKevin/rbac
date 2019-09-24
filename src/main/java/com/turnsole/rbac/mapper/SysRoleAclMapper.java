package com.turnsole.rbac.mapper;

import com.turnsole.rbac.domain.model.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleAcl record);

    void batchInsert(@Param("roleAclList") List<SysRoleAcl> roleAclList);

    SysRoleAcl selectByPrimaryKey(Integer id);

    List<SysRoleAcl> selectAll();

    int updateByPrimaryKey(SysRoleAcl record);

    List<Integer> getAclIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);

    void deleteByRoleId(@Param("roleId") int roleId);

    List<Integer> getRoleIdListByAclId(@Param("aclId") int aclId);
}