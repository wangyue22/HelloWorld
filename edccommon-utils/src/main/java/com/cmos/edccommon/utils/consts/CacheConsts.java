package com.cmos.edccommon.utils.consts;

/**
 * 应用缓存常量管理类
 * @author wangwei
 *
 */
public class CacheConsts {
	
	/**
	 * 接入Redis缓存的Key值管理
	 *   1.缓存键值定义格式：业务系统_业务模块:业务自定义键值。（不推荐含义不清的key和特别长的key）
	 *	2.业务系统：根据业务系统名称进行限定统一设置英文大写。
	 *	3.业务模块：由业务系统负责人统一对本系统的业务模块名称进行规划，统一配置为英文大写。业务模块可以进一步细化为一级业务模块，二级业务模块…级业务模块，模块间分割符统一采用下划线“_”进行表示。
	 *	4.业务自定义键值：由开发人员根据业务系统逻辑表示进行限定。 业务自定义键值应用“:”作为变量标识。
	 *	示例：
	 *	身份证认证应用的身份证真实性查验标识 GZT_RTN_FALSE:身份证号密文
	 *	EDCACMS_GZTRTNFALSE:20161215001
	*/
    public interface REDIS {
    	/*
    	 * 如果去国政通查验结果为不一致，则将不一致的身份证号和姓名加密后存入redis中，再次查验时会先查看redis中是否有，如果有，则不去国政通查验
    	 */
    	 String EDCACMS_GZT_RTNFALSE = "EDCACMS_GZT_RTNFALSE";
    }
    /**
     * 接入运营管理中心管理的JVM缓存Key值管理
     * 键值命名规则：系统名_业务名:自定义key
     * 由运营管理中心与业务应用在命名规则的基础上进行约自行约定
     * 示例：
	 *	调用国政通总放行开关，true打开放行，false关闭走实时接口
	 *	ACMS_SWITCH:SEND_GZT_PASS_FLAG
     */
    public interface JVM{
    	/*
    	 * 调用国政通总放行开关，true打开放行，false关闭走实时接口
    	 */
    	String SEND_GZT_PASS_FLAG = "ACMS_SWITCH:SEND_GZT_PASS_FLAG";
    }

}
