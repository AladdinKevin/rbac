package com.turnsole.rbac;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.turnsole.rbac.common.JsonData;
import com.turnsole.rbac.controller.SysDeptController;
import com.turnsole.rbac.domain.param.DeptParam;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**部门测试
 * @author:徐凯
 * @date:2019/9/24,10:16
 * @what I say:just look,do not be be
 */
public class SysDeptControllerTest extends RbacApplicationTests {

    @Autowired
    private SysDeptController deptController;

    @Test
    public void testSave(){
        DeptParam dept = DeptParam.builder().name("总裁办").parentId(0).seq(1).remark("设定总裁办为最高部门").build();
        DeptParam dept1 = DeptParam.builder().name("人事部").parentId(1).seq(1).remark("人事部").build();
        DeptParam dept11 = DeptParam.builder().name("行政部").parentId(2).seq(1).remark("行政部").build();
        DeptParam dept12 = DeptParam.builder().name("人力资源部").parentId(2).seq(2).remark("人力资源部").build();
        DeptParam dept2 = DeptParam.builder().name("产研部").parentId(1).seq(1).remark("产研部").build();
        DeptParam dept21 = DeptParam.builder().name("大数据组").parentId(3).seq(1).remark("大数据").build();
        DeptParam dept22 = DeptParam.builder().name("Java组").parentId(3).seq(1).remark("Java组").build();

        DeptParam dept3 = DeptParam.builder().name("风控部").parentId(1).seq(1).remark("风控部").build();

        JsonData jsonData3 = deptController.saveDept(dept3);
    }

    @Test
    public void testTree() throws JsonProcessingException {
        JsonData jsonData = deptController.deptTree();
        System.out.println(jsonData.toJsonString());
    }

    @Test
    public void testUpdate() {
        //此处id为我本地数据库中风控部的id,请对应修改
        DeptParam dept = DeptParam.builder().id(8).name("风控部").parentId(1).seq(1).remark("测试修改风控部的备注").build();
        deptController.updateDept(dept);
    }

    @Test
    public void testDeleteDept() {
        deptController.deleteDept(8);
    }
}
