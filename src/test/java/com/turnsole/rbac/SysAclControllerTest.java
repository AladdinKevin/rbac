package com.turnsole.rbac;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.turnsole.rbac.common.JsonData;
import com.turnsole.rbac.controller.SysAclController;
import com.turnsole.rbac.domain.beans.PageQuery;
import com.turnsole.rbac.domain.param.AclParam;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**权限点测试
 * @author:徐凯
 * @date:2019/9/24,15:57
 * @what I say:just look,do not be be
 */
public class SysAclControllerTest extends RbacApplicationTests {
    @Autowired
    private SysAclController sysAclController;

    @Test
    public void testSave() {
        //type 1 菜单 2 按钮 3 其它
        AclParam createOrderAcl = AclParam.builder().name("创建订单").aclmoduleId(1).type(1)
                .status(1).seq(1).remark("创建订单").build();
        AclParam updateOrderAcl = AclParam.builder().name("修改订单").aclmoduleId(1).type(1)
                .status(1).seq(2).remark("修改订单").build();
        AclParam createGoodsAcl = AclParam.builder().name("创建商品").aclmoduleId(2).type(1)
                .status(1).seq(3).remark("创建商品").build();
        AclParam testGoodsAcl = AclParam.builder().name("测试商品").aclmoduleId(2).type(1)
                .status(1).seq(4).remark("测试商品").build();
        sysAclController.saveAclModule(testGoodsAcl);
    }

    @Test
    public void testUpdate() {
        AclParam testGoodsAcl = AclParam.builder().id(4).name("测试商品").aclmoduleId(2).type(1)
                .status(1).seq(4).remark("测试商品beizhuxiugai").build();
        sysAclController.update(testGoodsAcl);
    }

    @Test
    public void testList() throws JsonProcessingException {
        PageQuery pq = new PageQuery();
        pq.setOffset(0);
        pq.setPageSize(5);
        JsonData list = sysAclController.list(1, pq);
        System.out.println(list.toJsonString());
    }

    /**
     * 拥有该权限的 角色和用户
     * @throws JsonProcessingException
     */
    @Test
    public void TestAcls() throws JsonProcessingException {
        //1 修改订单
        JsonData jsonData = sysAclController.acls(1);
        System.out.println(jsonData.toJsonString());
    }

}
