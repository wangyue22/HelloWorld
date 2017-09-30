package com.cmos.edccommon.dao.dept;

import com.cmos.edccommon.beans.dept.Dept;
import com.cmos.edccommon.beans.dept.DeptExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptDAO {

	List<Dept> getByExample(DeptExample example);
	
	Dept getByPrimaryKey(Long id);
	
	int deleteByPrimaryKey(Long id);
	
	int deleteByExample(DeptExample example);
	
	int save(Dept record);
	
	int saveSelective(Dept record);
	
	int countByExample(DeptExample example);
	
	int updateByExampleSelective(@Param("record") Dept record, @Param("example") DeptExample example);
	
	int updateByExample(@Param("record") Dept record, @Param("example") DeptExample example);
	
	int updateByPrimaryKeySelective(Dept record);
	
	int updateByPrimaryKey(Dept record);
	

}
