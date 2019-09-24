package com.turnsole.rbac;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.turnsole.rbac.common.JsonData;
import com.turnsole.rbac.controller.SysUserController;
import com.turnsole.rbac.domain.beans.PageQuery;
import com.turnsole.rbac.domain.param.UserParam;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**用户测试
 * @author:徐凯
 * @date:2019/9/24,16:53
 * @what I say:just look,do not be be
 */
public class SysUserControllerTest extends RbacApplicationTests {
    @Autowired
    private SysUserController sysUserController;

    @Test
    public void testSaveUser() {
        UserParam xukai = UserParam.builder().username("xukai").telephone("18813008034")
                .mail("18813008034@163.com").deptId(5).status(1).build();
        UserParam yshaoshaui = UserParam.builder().username("yshaoshaui").telephone("18813008035")
                .mail("18813008035@163.com").deptId(4).status(1).build();
        UserParam madam = UserParam.builder().username("madam").telephone("18813008036")
                .mail("18813008036@163.com").deptId(2).status(1).build();
        UserParam test = UserParam.builder().username("test").telephone("18813008037")
                .mail("test@163.com").deptId(2).status(1).build();
        sysUserController.saveUser(test);
    }

    @Test
    public void testUpdateUser() {
        UserParam test = UserParam.builder().id(4).username("test").telephone("18813008037")
                .mail("test111111@163.com").deptId(2).status(1).build();
        sysUserController.updateUser(test);
    }

    @Test
    public void testPage() throws JsonProcessingException {
        PageQuery pq = new PageQuery();
        pq.setOffset(0);
        pq.setPageSize(5);
        JsonData jsonData = sysUserController.page(2, pq);
        System.out.println(jsonData.toJsonString());
    }

}
