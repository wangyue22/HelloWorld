package com.cmos.edccommon.service.impl.comp;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.edccommon.beans.dept.Dept;
import com.cmos.edccommon.beans.dept.DeptExample;
import com.cmos.edccommon.beans.emp.Emp;
import com.cmos.edccommon.beans.emp.EmpExample;
import com.cmos.edccommon.iservice.comp.ICompSV;
import com.cmos.edccommon.iservice.dept.IDeptSV;
import com.cmos.edccommon.iservice.emp.IEmpSV;
import org.springframework.beans.factory.annotation.Autowired;
import com.cmos.common.exception.GeneralException;
import com.cmos.common.exception.DataAccessException;

import java.math.BigDecimal;
import java.util.List;

/**
 * 组合服务实现
 *
 * @author zhuanghd
 * @see ICompSV
 * @since 1.0
 */
@Service(group = "edccommon")
public class CompSVImpl implements ICompSV {

    @Autowired
    private IEmpSV empSV = null;

    @Autowired
    private IDeptSV deptSV = null;

    public CompSVImpl() {
    }

    @Override
    public int getEmpCountByDeptName(String name) throws GeneralException {
        // 查询部门编号
        DeptExample deptEx = new DeptExample();
        DeptExample.Criteria sql = deptEx.createCriteria();
        sql.andDnameEqualTo(name);
        List<Dept> depts = deptSV.getByExample(deptEx);
        if (depts.size() < 1) {
            throw new DataAccessException("DAE1001");
        }
        Dept dept = depts.get(0);
        long deptno = dept.getDeptno();
        // 查询员工数量
        EmpExample empEx = new EmpExample();
        EmpExample.Criteria sql2 = empEx.createCriteria();
        sql2.andDeptnoEqualTo(BigDecimal.valueOf(deptno));
        return empSV.countByExample(empEx);
    }

    @Override
    public int updateDeptno(long oldDeptno, long newDeptno) throws GeneralException {
        // 更新部门编号
        DeptExample deptEx = new DeptExample();
        DeptExample.Criteria sql = deptEx.createCriteria();
        sql.andDeptnoEqualTo(oldDeptno);
        Dept dept = new Dept();
        dept.setDeptno(newDeptno);
        int count = deptSV.updateByExampleSelective(dept, deptEx);
        if (count < 1) {
            throw new DataAccessException("DAE1002");
        }
        // 更新员工部门
        EmpExample empEx = new EmpExample();
        EmpExample.Criteria sql2 = empEx.createCriteria();
        sql2.andDeptnoEqualTo(BigDecimal.valueOf(oldDeptno));
        Emp emp = new Emp();
        emp.setDeptno(BigDecimal.valueOf(newDeptno));
        return empSV.updateByExampleSelective(emp, empEx);
    }

}
