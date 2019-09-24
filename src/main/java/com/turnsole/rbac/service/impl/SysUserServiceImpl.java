package com.turnsole.rbac.service.impl;

import com.google.common.base.Preconditions;
import com.turnsole.rbac.common.RequestHolder;
import com.turnsole.rbac.domain.beans.PageQuery;
import com.turnsole.rbac.domain.beans.PageResult;
import com.turnsole.rbac.domain.model.SysUser;
import com.turnsole.rbac.domain.param.UserParam;
import com.turnsole.rbac.exception.ParamException;
import com.turnsole.rbac.mapper.SysUserMapper;
import com.turnsole.rbac.service.ISysLogService;
import com.turnsole.rbac.service.ISysUserService;
import com.turnsole.rbac.util.BeanValidator;
import com.turnsole.rbac.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author:徐凯
 * @date:2019/8/5,17:53
 * @what I say:just look,do not be be
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Autowired
    private ISysLogService sysLogService;

    @Override
    public void save(UserParam param) {
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(), null)){
            throw new ParamException("","");
        }
        if (checkEmailExist(param.getMail(), param.getId())){
            throw new ParamException("","");
        }
        String password = "";
        //MD5加密
        String encryptedPassword = MD5Util.encrypt(password);
        SysUser user = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone())
                .mail(param.getMail()).deptId(param.getDeptId()).status(param.getStatus())
                .remark(param.getRemark()).password(encryptedPassword).build();
        user.setOperator(RequestHolder.getCurrentUser().getUsername());
//        user.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        user.setOperateTime(new Date());

        // todo sendEmail

        sysUserMapper.insert(user);
        sysLogService.saveUserLog(null,user);
    }

    @Override
    public void update(UserParam param){
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(), param.getId())){
            throw new ParamException("","");
        }
        if (checkEmailExist(param.getMail(), param.getId())){
            throw new ParamException("","");
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新用户不存在");
        SysUser after = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone())
                .mail(param.getMail()).deptId(param.getDeptId()).status(param.getStatus())
                .remark(param.getRemark()).password(before.getPassword()).
                        id(param.getId()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
//        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        sysUserMapper.updateByPrimaryKey(after);
        sysLogService.saveUserLog(before,after);
    }

    @Override
    public SysUser findByKeyword(String keyword) {
        return sysUserMapper.findByKeyword(keyword);
    }

    /**
     * 分页查询
     * @param deptId
     * @param page
     * @return
     */
    @Override
    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page) {
        BeanValidator.check(page);
        int count = sysUserMapper.countByDeptId(deptId);
        if (count > 0){
            List<SysUser> data = sysUserMapper.getPageByDeptId(deptId, page);
            return PageResult.<SysUser>builder().data(data).total(count).build();
        }
        return PageResult.<SysUser>builder().build();
    }

    @Override
    public List<SysUser> getAll() {
        return sysUserMapper.selectAll();
    }

    private boolean checkEmailExist(String email, Integer userId){
        return sysUserMapper.countByMail(email,userId) > 0;
    }

    private boolean checkTelephoneExist(String telephone, Integer userId){
        return sysUserMapper.countByTelephone(telephone, userId) > 0;
    }
}
