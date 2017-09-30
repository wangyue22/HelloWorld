package com.cmos.edccommon.web.controller.emp;

/**
 * 更新部门编号参数
 *
 * @author zhuanghd
 * @since 1.0
 */
public class UpdateDeptnoParam {

    private Long oldDeptno = null;

    private Long newDeptno = null;

    public Long getOldDeptno() {
        return oldDeptno;
    }

    public void setOldDeptno(Long oldDeptno) {
        this.oldDeptno = oldDeptno;
    }

    public Long getNewDeptno() {
        return newDeptno;
    }

    public void setNewDeptno(Long newDeptno) {
        this.newDeptno = newDeptno;
    }

}
