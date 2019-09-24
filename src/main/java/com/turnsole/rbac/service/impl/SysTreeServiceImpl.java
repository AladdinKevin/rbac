package com.turnsole.rbac.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.turnsole.rbac.domain.dto.AclDto;
import com.turnsole.rbac.domain.dto.AclModuleLevelDto;
import com.turnsole.rbac.domain.dto.DeptLevelDto;
import com.turnsole.rbac.domain.model.SysAcl;
import com.turnsole.rbac.domain.model.SysAclmodule;
import com.turnsole.rbac.domain.model.SysDept;
import com.turnsole.rbac.mapper.SysAclMapper;
import com.turnsole.rbac.mapper.SysAclmoduleMapper;
import com.turnsole.rbac.mapper.SysDeptMapper;
import com.turnsole.rbac.service.ISysCoreService;
import com.turnsole.rbac.service.ISysTreeService;
import com.turnsole.rbac.util.LevelUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author:徐凯
 * @date:2019/8/1,18:23
 * @what I say:just look,do not be be
 */
@Service
public class SysTreeServiceImpl implements ISysTreeService {

    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysAclmoduleMapper sysAclmoduleMapper;
    @Resource
    private ISysCoreService sysCoreService;
    @Resource
    private SysAclMapper sysAclMapper;

    @Override
    public List<AclModuleLevelDto> aclModuleTree() {
        List<SysAclmodule> aclmoduleList = sysAclmoduleMapper.getAllAclModule();
        List<AclModuleLevelDto> dtoList = Lists.newArrayList();
        for (SysAclmodule aclmodule : aclmoduleList){
            dtoList.add(AclModuleLevelDto.adapt(aclmodule));
        }
        return aclModuleListToTree(dtoList);
    }

    @Override
    public List<AclModuleLevelDto> roleTree(int roleId) {
        //1 当前用户已经分配的权限点
        List<SysAcl> userAclList = sysCoreService.getCurrentUserAclList();
        //2 当前角色分配的权限点
        List<SysAcl> roleAclList = sysCoreService.getRoleAclList(roleId);
        //3 当前系统所有权限点
        List<AclDto> aclDtoList = Lists.newArrayList();

        Set<Integer> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        Set<Integer> roleAclIdSet = roleAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());

        List<SysAcl> allAclList = sysAclMapper.getAll();

        for (SysAcl acl : allAclList) {
            AclDto dto = AclDto.adapt(acl);
            if (userAclIdSet.contains(dto.getId())){
                dto.setHasAcl(true);
            }
            if (roleAclIdSet.contains(dto.getId())){
                dto.setChecked(true);
            }
            aclDtoList.add(dto);
        }
        //获取到的是角色对应的权限树
        return aclListToTree(aclDtoList);
    }

    //根据指定用户id 获取权限树
    @Override
    public List<AclModuleLevelDto> userAlcTree(int userId) {
        //去除当前用户已经分配的权限点
        List<SysAcl> userAclList = sysCoreService.getUserAclList(userId);
        List<AclDto> aclDtoList = Lists.newArrayList();
        for (SysAcl acl : userAclList){
            AclDto dto = AclDto.adapt(acl);
            dto.setHasAcl(true);
            dto.setChecked(true);
            aclDtoList.add(dto);
        }
        return aclListToTree(aclDtoList);
    }

    //获取权限模块树
    private List<AclModuleLevelDto> aclListToTree(List<AclDto> aclDtoList) {
        if (CollectionUtils.isEmpty(aclDtoList)){
            return Lists.newArrayList();
        }
        List<AclModuleLevelDto> aclModuleLevelDtoList = aclModuleTree();
        Multimap<Integer, AclDto> moduleIdAclMap = ArrayListMultimap.create();
        for (AclDto acl : aclDtoList) {
            if (acl.getStatus() == 1) {
                moduleIdAclMap.put(acl.getAclmoduleId(), acl);
            }
        }
        bindAclsWithOrder(aclModuleLevelDtoList, moduleIdAclMap);
        return aclModuleLevelDtoList;
    }

    private void bindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelDtoList, Multimap<Integer, AclDto> moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleLevelDtoList)){
            return;
        }
        for (AclModuleLevelDto dto : aclModuleLevelDtoList){
            //当前模块下所有权限点
            List<AclDto> aclDtoList = (List<AclDto>) moduleIdAclMap.get(dto.getId());
            if (CollectionUtils.isNotEmpty(aclDtoList)){
                Collections.sort(aclDtoList,aclSeqComparator);
                dto.setAclLevelList(aclDtoList);
            }
            bindAclsWithOrder(dto.getAclModuleLevelList(), moduleIdAclMap);
        }
    }

    /**
     * 数据转换成权限树
     * @param dtoList
     * @return
     */
    public List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)){
            return Lists.newArrayList();
        }
        Multimap<String,AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();
        List<AclModuleLevelDto> rootList = Lists.newArrayList();

        for (AclModuleLevelDto dto : dtoList) {
            levelAclModuleMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        Collections.sort(rootList,aclModuleLevelDtoComparator);
        transformAclModuleTree(rootList, LevelUtil.ROOT, levelAclModuleMap);
        return rootList;
    }

    public void transformAclModuleTree(List<AclModuleLevelDto> dtoList, String level,
                                       Multimap levelAclModuleMap) {
        for (int i = 0; i < dtoList.size(); i++) {
            AclModuleLevelDto dto = dtoList.get(i);
            String nextLevel = LevelUtil.calculateLevel(level, dto.getId());
            List<AclModuleLevelDto> tempList = (List<AclModuleLevelDto>) levelAclModuleMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempList)){
                Collections.sort(tempList, aclModuleLevelDtoComparator);
                dto.setAclModuleLevelList(tempList);
                transformAclModuleTree(tempList, nextLevel, levelAclModuleMap);
            }
        }
    }

    /**
     * 比较器
     */
    public Comparator<AclModuleLevelDto> aclModuleLevelDtoComparator = new Comparator<AclModuleLevelDto>() {
        @Override
        public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };


    @Override
    public List<DeptLevelDto> deptTree(){
        List<SysDept> deptList = sysDeptMapper.selectAll();

        List<DeptLevelDto> deptLevelDtoList = Lists.newArrayList();
        for (SysDept dept : deptList){
            DeptLevelDto dto = DeptLevelDto.adapt(dept);
            deptLevelDtoList.add(dto);
        }
        return deptLevelToTree(deptLevelDtoList);
    }

    private List<DeptLevelDto> deptLevelToTree(List<DeptLevelDto> deptLevelDtoList) {
        if (CollectionUtils.isEmpty(deptLevelDtoList)){
            return Lists.newArrayList();
        }
        //level -> (dept1, dept2, ...)
        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        List<DeptLevelDto> rootList = Lists.newArrayList();
        // 先取出所有root层级的部门
        for (DeptLevelDto dto : deptLevelDtoList){
            //ArrayListMultimap 会将key相同的对象，放入同一个集合存储
            levelDeptMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())){
                rootList.add(dto);
            }
        }
        //按照seq从小到大排序
        Collections.sort(rootList, deptSeqComparator);

        //递归生成树
        transformDeptTree(rootList,LevelUtil.ROOT, levelDeptMap);
        return rootList;
    }

    /**
     * 从root开始，会将每一个部门的子部门集合存储到自身部门Dto中的DeptList
     * 而最一开始传入的 根rotList中的内容会越来越庞大，知道递归完成
     * @param parentDeptLevelDtoList
     * @param parentLevel
     * @param levelDeptMap`
     */
    private void transformDeptTree(List<DeptLevelDto> parentDeptLevelDtoList, String parentLevel, Multimap<String, DeptLevelDto> levelDeptMap){
        for (int i = 0; i < parentDeptLevelDtoList.size(); i++) {
            //遍历该层的每个元素
            DeptLevelDto parentDeptLevelDto = parentDeptLevelDtoList.get(i);
            //处理当前层级的数据,根据当前dto的上级level和自身id 计算下一个层级的level
            String nextLevel = LevelUtil.calculateLevel(parentLevel, parentDeptLevelDto.getId());
            //处理下一层
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)){
                //排序
                Collections.sort(tempDeptList, deptSeqComparator);
                //把当前部门下的所有子部门放入当前 parentDeptLevelDto中
                parentDeptLevelDto.setDeptList(tempDeptList);
                //进入下一层处理
                transformDeptTree(tempDeptList, nextLevel, levelDeptMap);
            }
        }
    }

    private Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    private Comparator<AclDto> aclSeqComparator = new Comparator<AclDto>() {
        @Override
        public int compare(AclDto o1, AclDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

}
