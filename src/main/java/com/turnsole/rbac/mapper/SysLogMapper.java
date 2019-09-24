package com.turnsole.rbac.mapper;

import com.turnsole.rbac.domain.beans.PageQuery;
import com.turnsole.rbac.domain.dto.SearchLogDto;
import com.turnsole.rbac.domain.model.SysLog;
import com.turnsole.rbac.domain.model.SysLogWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLog record);

    SysLogWithBLOBs selectByPrimaryKey(Integer id);

    List<SysLog> selectAll();

    int updateByPrimaryKey(SysLog record);

    int countBySerchDto(@Param("dto")SearchLogDto dto);

    List<SysLogWithBLOBs> getPageListBySearchDto(@Param("dto")SearchLogDto dto,@Param("page") PageQuery page);
}