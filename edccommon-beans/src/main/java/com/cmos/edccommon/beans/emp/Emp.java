package com.cmos.edccommon.beans.emp;

import com.cmos.common.bean.GenericBean;

import java.math.BigDecimal;  
import java.util.Date;  

public class Emp extends GenericBean{

	private static final long serialVersionUID = 1L;

    /**
     * This field corresponds to the database column emp.EMPNO
     *
     * @date 2016-11-28 11:10:31
     */  
	private Long empno;
	
    /**
     * This field corresponds to the database column emp.ENAME
     *
     * @date 2016-11-28 11:10:31
     */  
	private String ename;
	
    /**
     * This field corresponds to the database column emp.JOB
     *
     * @date 2016-11-28 11:10:31
     */  
	private String job;
	
    /**
     * This field corresponds to the database column emp.MGR
     *
     * @date 2016-11-28 11:10:31
     */  
	private Long mgr;
	
    /**
     * This field corresponds to the database column emp.HIREDATE
     *
     * @date 2016-11-28 11:10:31
     */  
	private Date hiredate;
	
    /**
     * This field corresponds to the database column emp.SAL
     *
     * @date 2016-11-28 11:10:31
     */  
	private BigDecimal sal;
	
    /**
     * This field corresponds to the database column emp.COMM
     *
     * @date 2016-11-28 11:10:31
     */  
	private BigDecimal comm;
	
    /**
     * This field corresponds to the database column emp.DEPTNO
     *
     * @date 2016-11-28 11:10:31
     */  
	private BigDecimal deptno;
	
    /**
     * This method returns the value of the database column emp.EMPNO
     *
     * @return the value of emp.EMPNO
     *
     * @date 2016-11-28 11:10:31
     */
	public Long getEmpno() {  
        return empno;  
    }  
    /**
     * This method sets the value of the database column emp.EMPNO
     *
     * @param empno the value for emp.EMPNO
     *
     * @date 2016-11-28 11:10:31
     */
    public void setEmpno(Long empno) {  
        this.empno = empno;
    }

    /**
     * This method returns the value of the database column emp.ENAME
     *
     * @return the value of emp.ENAME
     *
     * @date 2016-11-28 11:10:31
     */
	public String getEname() {  
        return ename;  
    }  
    /**
     * This method sets the value of the database column emp.ENAME
     *
     * @param ename the value for emp.ENAME
     *
     * @date 2016-11-28 11:10:31
     */
    public void setEname(String ename) {  
        this.ename = ename == null ? null : ename.trim();
    }

    /**
     * This method returns the value of the database column emp.JOB
     *
     * @return the value of emp.JOB
     *
     * @date 2016-11-28 11:10:31
     */
	public String getJob() {  
        return job;  
    }  
    /**
     * This method sets the value of the database column emp.JOB
     *
     * @param job the value for emp.JOB
     *
     * @date 2016-11-28 11:10:31
     */
    public void setJob(String job) {  
        this.job = job == null ? null : job.trim();
    }

    /**
     * This method returns the value of the database column emp.MGR
     *
     * @return the value of emp.MGR
     *
     * @date 2016-11-28 11:10:31
     */
	public Long getMgr() {  
        return mgr;  
    }  
    /**
     * This method sets the value of the database column emp.MGR
     *
     * @param mgr the value for emp.MGR
     *
     * @date 2016-11-28 11:10:31
     */
    public void setMgr(Long mgr) {  
        this.mgr = mgr;
    }

    /**
     * This method returns the value of the database column emp.HIREDATE
     *
     * @return the value of emp.HIREDATE
     *
     * @date 2016-11-28 11:10:31
     */
	public Date getHiredate() {  
        return hiredate;  
    }  
    /**
     * This method sets the value of the database column emp.HIREDATE
     *
     * @param hiredate the value for emp.HIREDATE
     *
     * @date 2016-11-28 11:10:31
     */
    public void setHiredate(Date hiredate) {  
        this.hiredate = hiredate;
    }

    /**
     * This method returns the value of the database column emp.SAL
     *
     * @return the value of emp.SAL
     *
     * @date 2016-11-28 11:10:31
     */
	public BigDecimal getSal() {  
        return sal;  
    }  
    /**
     * This method sets the value of the database column emp.SAL
     *
     * @param sal the value for emp.SAL
     *
     * @date 2016-11-28 11:10:31
     */
    public void setSal(BigDecimal sal) {  
        this.sal = sal;
    }

    /**
     * This method returns the value of the database column emp.COMM
     *
     * @return the value of emp.COMM
     *
     * @date 2016-11-28 11:10:31
     */
	public BigDecimal getComm() {  
        return comm;  
    }  
    /**
     * This method sets the value of the database column emp.COMM
     *
     * @param comm the value for emp.COMM
     *
     * @date 2016-11-28 11:10:31
     */
    public void setComm(BigDecimal comm) {  
        this.comm = comm;
    }

    /**
     * This method returns the value of the database column emp.DEPTNO
     *
     * @return the value of emp.DEPTNO
     *
     * @date 2016-11-28 11:10:31
     */
	public BigDecimal getDeptno() {  
        return deptno;  
    }  
    /**
     * This method sets the value of the database column emp.DEPTNO
     *
     * @param deptno the value for emp.DEPTNO
     *
     * @date 2016-11-28 11:10:31
     */
    public void setDeptno(BigDecimal deptno) {  
        this.deptno = deptno;
    }

}