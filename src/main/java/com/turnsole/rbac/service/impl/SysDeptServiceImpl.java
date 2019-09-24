package com.turnsole.rbac.service.impl;

import com.google.common.base.Preconditions;
import com.turnsole.rbac.common.RequestHolder;
import com.turnsole.rbac.domain.model.SysDept;
import com.turnsole.rbac.domain.param.DeptParam;
import com.turnsole.rbac.exception.ParamException;
import com.turnsole.rbac.mapper.SysDeptMapper;
import com.turnsole.rbac.mapper.SysUserMapper;
import com.turnsole.rbac.service.ISysDeptService;
import com.turnsole.rbac.service.ISysLogService;
import com.turnsole.rbac.util.BeanValidator;
import com.turnsole.rbac.util.LevelUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author:徐凯
 * @date:2019/8/1,17:28
 * @what I say:just look,do not be be
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ISysLogService sysLogService;

    @Override
    public void save(DeptParam param){
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())){
          throw new ParamException("","同一层级下存在相同名称的部门");
        }
        SysDept dept = SysDept.builder().name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();
        dept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()),param.getParentId()));
        dept.setOperator(RequestHolder.getCurrentUser().getUsername());
        //dept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        dept.setOperateTime(new Date());
        sysDeptMapper.insert(dept);
        sysLogService.saveDeptLog(null,dept);
    }

    /**
     * 更新操作
     * @param dept
     */
    @Override
    public void update(DeptParam dept) {
        BeanValidator.check(dept);
        SysDept before = sysDeptMapper.selectByPrimaryKey(dept.getId());
        Preconditions.checkNotNull(before,"待更新的部门不存在");

        SysDept after = SysDept.builder().id(dept.getId()).name(dept.getName()).parentId(dept.getParentId())
                .seq(dept.getSeq()).remark(dept.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(dept.getParentId()),dept.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
//        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        updateWithChild(before, after);
        sysLogService.saveDeptLog(before,after);
    }

    /**
     * 部门删除操作
     * @param id
     */
    @Override
    public void deleteDept(int id) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(id);
        //欲删除部门不存在,无法删除
        Preconditions.checkNotNull(dept,"待删除的部门不存在，无法删除");
        //存在子部门无法删除
        if (sysDeptMapper.countByParentId(id) > 0){
            throw new ParamException("","当前部门下面有子部门,无法删除");
        }
        //存在用户无法删除
        if (sysUserMapper.countByDeptId(id) > 0){
            throw new ParamException("","当前部门下面有用户,无法删除");
        }
        sysDeptMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    void updateWithChild(SysDept before, SysDept after){
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())){
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(before.getLevel());
            if (CollectionUtils.isNotEmpty(deptList)){
                for (SysDept dept : deptList){
                    String level = dept.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0){
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }
        sysDeptMapper.updateByPrimaryKey(after);
    }

    /**
     * 检查当前部门是否存在
     * @param parentId
     * @param deptName
     * @param deptId
     * @return
     */
    private boolean checkExist(Integer parentId, String deptName, Integer deptId){
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }

    /**
     * 获取当前部门的层级
     * @param deptId
     * @return
     */
    private String getLevel(Integer deptId){
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (dept == null){
            return null;
        }
        return dept.getLevel();
    }
}
