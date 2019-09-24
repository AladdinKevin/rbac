package com.turnsole.rbac;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.turnsole.rbac.common.JsonData;
import com.turnsole.rbac.controller.SysAclModuleController;
import com.turnsole.rbac.domain.param.AclModuleParam;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/** 权限模块测试
 * @author:徐凯
 * @date:2019/9/24,15:35
 * @what I say:just look,do not be be
 */
public class SysAclModuleControllerTest extends RbacApplicationTests {
    @Autowired
    private SysAclModuleController sysAclModuleController;

    @Test
    public void saveDept() {
        AclModuleParam aclModule = AclModuleParam.builder().name("订单权限")
            .parentId(0).status(1).seq(1).remark("订单模块").build();
        AclModuleParam shopAclModule = AclModuleParam.builder().name("商品权限")
                .parentId(0).status(1).seq(2).remark("商品权限").build();
        AclModuleParam customerAclModule = AclModuleParam.builder().name("客户权限")
                .parentId(0).status(1).seq(3).remark("客户权限").build();
        AclModuleParam aclModuleSub1 = AclModuleParam.builder().name("订单子权限1")
                .parentId(1).status(1).seq(1).remark("订单模块").build();
        AclModuleParam testAclModule = AclModuleParam.builder().name("测试权限")
                .parentId(0).status(1).seq(4).build();
        sysAclModuleController.saveDept(aclModuleSub1);
        sysAclModuleController.saveDept(testAclModule);
    }

    @Test
    public void updateDept() {
        AclModuleParam testAclModule = AclModuleParam.builder().id(5).name("测试权限")
                .parentId(0).status(1).seq(4).remark("测试update的时候添加的哈哈哈").build();
        sysAclModuleController.updateAclModule(testAclModule);
    }

    @Test
    public void tree() throws JsonProcessingException {
        JsonData tree = sysAclModuleController.tree();
        System.out.println(tree.toJsonString());
    }

    @Test
    public void testDelete() {
        sysAclModuleController.deleteAcl(5);
    }

}
