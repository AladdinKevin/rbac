package com.turnsole.rbac;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.turnsole.rbac.common.JsonData;
import com.turnsole.rbac.controller.SysLogController;
import com.turnsole.rbac.domain.beans.PageQuery;
import com.turnsole.rbac.domain.param.SearchLogParam;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author:徐凯
 * @date:2019/9/24,17:42
 * @what I say:just look,do not be be
 */
public class SysLogControllerTest extends RbacApplicationTests {
    @Autowired
    private SysLogController sysLogController;

    @Test
    public void testPage() throws JsonProcessingException {
        SearchLogParam searchLogParam = SearchLogParam.builder().operator("admin")
                .type(1).build();
        PageQuery pq = new PageQuery();
        pq.setOffset(0);
        pq.setPageSize(10);
        JsonData jsonData = sysLogController.page(searchLogParam, pq);
        System.out.println(jsonData.toJsonString());
    }

    /**
     * 通过日志,对之前的修改操作进行还原
     */
    @Test
    public void testRecover(){
        /**
         * oldvalue:{"id":5,"name":"测试专用角色","type":1,"status":1,"remark":"测试专用角色",
         *           "operator":"admin","operateTime":1569313926000}
         * newvalue:{"id":5,"name":"测试专用角色","type":1,"status":1,"remark":"测试专用角色updatehahahaha"
         *           ,"operator":"admin","operateTime":1569314930613}
         */
        //效果为:id 5的这个角色的信息 还原为oldvalue所展示的信息
        sysLogController.recover(32);
        //执行完成之后我们查看id为5的这个role 发现数据已经还原了
    }

}
