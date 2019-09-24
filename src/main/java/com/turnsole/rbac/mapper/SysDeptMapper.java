package com.turnsole.rbac.mapper;

import com.turnsole.rbac.domain.model.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDept record);

    SysDept selectByPrimaryKey(Integer id);

    List<SysDept> selectAll();

    int updateByPrimaryKey(SysDept record);

    List<SysDept> getChildDeptListByLevel(@Param("level")String level);

    void batchUpdateLevel(@Param("sysDeptList")List<SysDept> sysDeptList);

    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id")Integer id);

    int countByParentId(@Param("deptId") int deptId);

}