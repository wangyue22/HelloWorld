package com.cmos.edccommon.beans.dept;

import com.cmos.common.bean.GenericBean;

public class Dept extends GenericBean{

	private static final long serialVersionUID = 1L;

    /**
     * This field corresponds to the database column dept.DEPTNO
     *
     * @date 2016-11-28 11:00:11
     */  
	private Long deptno;
	
    /**
     * This field corresponds to the database column dept.DNAME
     *
     * @date 2016-11-28 11:00:11
     */  
	private String dname;
	
    /**
     * This field corresponds to the database column dept.LOC
     *
     * @date 2016-11-28 11:00:11
     */  
	private String loc;
	
    /**
     * This method returns the value of the database column dept.DEPTNO
     *
     * @return the value of dept.DEPTNO
     *
     * @date 2016-11-28 11:00:11
     */
	public Long getDeptno() {  
        return deptno;  
    }  
    /**
     * This method sets the value of the database column dept.DEPTNO
     *
     * @param deptno the value for dept.DEPTNO
     *
     * @date 2016-11-28 11:00:11
     */
    public void setDeptno(Long deptno) {  
        this.deptno = deptno;
    }

    /**
     * This method returns the value of the database column dept.DNAME
     *
     * @return the value of dept.DNAME
     *
     * @date 2016-11-28 11:00:11
     */
	public String getDname() {  
        return dname;  
    }  
    /**
     * This method sets the value of the database column dept.DNAME
     *
     * @param dname the value for dept.DNAME
     *
     * @date 2016-11-28 11:00:11
     */
    public void setDname(String dname) {  
        this.dname = dname == null ? null : dname.trim();
    }

    /**
     * This method returns the value of the database column dept.LOC
     *
     * @return the value of dept.LOC
     *
     * @date 2016-11-28 11:00:11
     */
	public String getLoc() {  
        return loc;  
    }  
    /**
     * This method sets the value of the database column dept.LOC
     *
     * @param loc the value for dept.LOC
     *
     * @date 2016-11-28 11:00:11
     */
    public void setLoc(String loc) {  
        this.loc = loc == null ? null : loc.trim();
    }

}