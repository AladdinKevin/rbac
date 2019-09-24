package com.turnsole.rbac.mapper;

import com.turnsole.rbac.domain.model.SysAclmodule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclmoduleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAclmodule record);

    SysAclmodule selectByPrimaryKey(Integer id);

    List<SysAclmodule> selectAll();

    int updateByPrimaryKey(SysAclmodule record);

    void batchUpdateLevel(@Param("sysAclModuleList")List<SysAclmodule> sysAclmoduleList);

    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id")Integer id);

    List<SysAclmodule> getChildDeptListByLevel(@Param("level") String level);

    List<SysAclmodule> getAllAclModule();

    int countByParentId(@Param("aclModuleId") int aclModuleId);
}