package com.turnsole.rbac;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.turnsole.rbac.common.JsonData;
import com.turnsole.rbac.controller.SysRoleController;
import com.turnsole.rbac.domain.param.SysRoleParam;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**角色测试
 * @author:徐凯
 * @date:2019/9/24,16:24
 * @what I say:just look,do not be be
 */
public class SysRoleControllerTest extends RbacApplicationTests {
    @Autowired
    private SysRoleController sysRoleController;

    @Test
    public void testSave() {
        SysRoleParam roleParam = SysRoleParam.builder().name("总裁").type(1)
                .status(1).remark("总裁").build();
        SysRoleParam roleParam1 = SysRoleParam.builder().name("技术总监").type(1)
                .status(1).remark("技术总监").build();
        SysRoleParam roleParam2 = SysRoleParam.builder().name("运营总监").type(1)
                .status(1).remark("运营总监").build();
        SysRoleParam roleParam3 = SysRoleParam.builder().name("人事总监").type(1)
                .status(1).remark("人事总监").build();
        SysRoleParam testRole = SysRoleParam.builder().name("测试专用角色").type(1)
                .status(1).remark("测试专用角色").build();
        sysRoleController.save(testRole);
    }

    @Test
    public void testUpdate() {
        SysRoleParam testRole = SysRoleParam.builder().id(5).name("测试专用角色").type(1)
                .status(1).remark("测试专用角色updatehahahaha").build();
        sysRoleController.update(testRole);
    }

    @Test
    public void testList() throws JsonProcessingException {
        JsonData list = sysRoleController.list();
        System.out.println(list.toJsonString());
    }

    /**
     * 给角色分配权限点
     */
    @Test
    public void TestChangeAcls() {
        //先将现有全部权限分配给总裁这个角色
        /**
         * 总裁 roleId 1
         * 权限 1,2,3,4
         */
        sysRoleController.changeAcls(1,"1,2,3,4");
    }

    /**
     * 给用户分配角色
     */
    @Test
    public void TestChangeUsers() {
        /**
         * 将总裁的角色分配给用户xukai
         * roleId 1
         * userId 1
         */
        sysRoleController.changeUsers(1,"1");
    }

    /**
     * 某个角色拥有的全部权限
     */
    @Test
    public void TestRoleTree() throws JsonProcessingException {
        JsonData jsonData = sysRoleController.roleTree(1);
        System.out.println(jsonData.toJsonString());
    }

    @Test
    public void TestUsers() throws JsonProcessingException {
        JsonData jsonData = sysRoleController.users(1);
        System.out.println(jsonData.toJsonString());
    }

}
