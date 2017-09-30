package com.cmos.edccommon.web.controller.emp;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.edccommon.beans.emp.Emp;
import com.cmos.edccommon.iservice.comp.ICompSV;
import com.cmos.edccommon.iservice.emp.IEmpSV;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuzh on 2016/11/18.
 */

@RestController
@RequestMapping("/dept")
@Validated
public class DeptController {

    @Reference(group = "edccommon")
    private IEmpSV empSV = null;

    @Reference(group = "edccommon")
    private ICompSV compSV = null; // 组合服务



    @RequestMapping(value = "/update-deptno", method = RequestMethod.POST)
    public Map getEmpCountByDeptName(@RequestBody UpdateDeptnoParam param) {
        Map result = new HashMap();
        try {
            compSV.updateDeptno(param.getOldDeptno(), param.getNewDeptno());
            result.put("result", "ok");
        } catch (Exception e) {
            //do log ... e.printStackTrace();
            e.printStackTrace();
            result.put("error", e.getMessage());
        }
        return result;
    }
}
