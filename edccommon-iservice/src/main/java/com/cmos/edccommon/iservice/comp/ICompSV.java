package com.cmos.edccommon.iservice.comp;

import com.cmos.common.exception.GeneralException;

/**
 * 组合服务
 *
 * @author zhuanghd
 * @since 1.0
 */
public interface ICompSV {

    /**
     * 更新部门名称获取员工数
     * @throws Exception
     */
    int getEmpCountByDeptName(String name) throws GeneralException;


    /**
     * 更新部门编号
     * @throws Exception
     */
    int updateDeptno(long oldDeptno, long newDeptno) throws GeneralException;

}
