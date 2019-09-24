package com.turnsole.rbac;

import com.turnsole.rbac.common.RequestHolder;
import com.turnsole.rbac.domain.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**单元测试让代码更健壮
 * author:徐凯
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RbacApplicationTests {

    @Before
    public void init() {
        log.info("rbac 测试开始 ############################");
        //模拟用户登录后将用户信息存入RequestHolder 此处该用户已经分配了角色
        SysUser user = SysUser.builder().id(1).username("admin").status(1).build();
        RequestHolder.add(user);
    }

    @After
    public void after() {
        log.info("rbac 测试结束 ############################");
    }

}
