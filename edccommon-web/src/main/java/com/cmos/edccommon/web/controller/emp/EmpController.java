package com.cmos.edccommon.web.controller.emp;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.edccommon.beans.emp.Emp;
import com.cmos.edccommon.iservice.comp.ICompSV;
import com.cmos.edccommon.iservice.emp.IEmpSV;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuzh on 2016/11/18.
 */

@RestController
@RequestMapping("/emp")
@Validated
public class EmpController {

    @Reference(group = "edccommon")
    private IEmpSV empSV = null;

    @Reference(group = "edccommon")
    private ICompSV compSV = null; // 组合服务



    /**
     * 根据EID查询员工信息
     *
     * @param eid
     * @return
     */
    @RequestMapping(value = "/{eid}", method = RequestMethod.GET)
    public Emp getEmp(@PathVariable long eid) {
        return empSV.getByPrimaryKey(eid);
    }

    /**
     * 根据EID删除员工信息
     *
     * @param eid
     * @return
     */
    @RequestMapping(value = "/{eid}", method = RequestMethod.DELETE)
    public void delEmp(@PathVariable long eid) {
        empSV.deleteByPrimaryKey(eid);
    }

    /**
     * 保存员工信息
     *
     * @param emp
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public void saveEmp(Emp emp) {
        empSV.save(emp);
    }

    /**
     * 修改员工信息
     *
     * @param emp
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public void updateEmp(Emp emp) {
        empSV.updateByPrimaryKey(emp);
    }

    @RequestMapping(value = "/count/{deptName}", method = RequestMethod.GET)
    public Map getEmpCountByDeptName(@PathVariable String deptName) {
        Map result = new HashMap();
        try {
            int count = compSV.getEmpCountByDeptName(deptName);
            result.put("count", count);
        } catch (Exception e) {
            //do log ... e.printStackTrace();
            result.put("error", e.getMessage());
        }
        return result;
    }

}
