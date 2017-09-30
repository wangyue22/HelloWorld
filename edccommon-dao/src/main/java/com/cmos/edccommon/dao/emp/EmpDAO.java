package com.cmos.edccommon.dao.emp;


import com.cmos.edccommon.beans.emp.Emp;
import com.cmos.edccommon.beans.emp.EmpExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Demo示例,DAO查询接口
 * <p>该接口无需实现类,MyBatis会自动代理该接口实现底层查询
 *
 * @author zhuanghd
 * @since 1.0
 */
public interface EmpDAO {

    /**
     * 根据Criteria查询条件查询员工信息
     *
     * @param example Criteria查询条件
     * @return 符合查询条件的员工对象列表
     */
    List<Emp> getByExample(EmpExample example);

    /**
     * 根据id查询员工信息
     *
     * @param id 员工id
     * @return 员工对象
     */
    Emp getByPrimaryKey(Long id);

    /**
     * 根据id删除员工信息
     *
     * @param id 员工id
     * @return 删除的记录条数, 若删除成功则返回1
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 根据Criteria查询条件删除员工信息
     *
     * @param example Criteria查询条件
     * @return 若删除成功则返回删除的记录条数
     */
    int deleteByExample(EmpExample example);

    /**
     * 插入一个员工对象
     *
     * @param record 员工对象
     * @return 保存的记录条数, 若保存成功则返回1
     */
    int save(Emp record);

    /**
     * 插入一个员工对象
     * <p>只插入不为null的字段,不会影响有默认值的字段
     *
     * @param record 员工对象
     * @return 保存的记录条数, 若保存成功则返回1
     */
    int saveSelective(Emp record);

    /**
     * 根据Criteria查询条件查询符合条件的员工记录数量
     *
     * @param example Criteria查询条件
     * @return 符合条件的员工记录数量
     */
    int countByExample(EmpExample example);

    /**
     * 根据Criteria查询条件更新员工信息
     * <p>只更新不为null的字段,不会影响有默认值的字段
     *
     * @param record 员工信息
     * @param example Criteria查询条件
     * @return 若更新成功则返回更新的记录条数
     */
    int updateByExampleSelective(@Param("record") Emp record, @Param("example") EmpExample example);

    /**
     * 根据Criteria查询条件更新员工信息
     *
     * @param record 员工信息
     * @param example Criteria查询条件
     * @return 若更新成功则返回更新的记录条数
     */
    int updateByExample(@Param("record") Emp record, @Param("example") EmpExample example);

    /**
     * 根据主键更新员工信息
     * <p>只更新不为null的字段,不会影响有默认值的字段
     * <p>调用此接口时,要注意主键不能为空
     *
     * @param record 员工信息
     * @return 若更新成功则返回更新的记录条数
     */
    int updateByPrimaryKeySelective(Emp record);

    /**
     * 根据主键更新员工信息
     * <p>调用此接口时,要注意主键不能为空
     *
     * @param record 员工信息
     * @return 若更新成功则返回更新的记录条数
     */
    int updateByPrimaryKey(Emp record);

}
