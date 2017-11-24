package com.cmos.edccommon.utils.consts;

/**
 * 应用缓存常量管理类
 * @author wangwei
 *
 */
public class CacheConsts {
    
    public static final String FLOW_CONTROLLER_PARAM_KEY="sourceSystem";

    /**
     * 各系统公用工具类中的开关key前缀
     */
    public static final String CACHE_SWITCH_PREFIX = "CO_SWITCH:";

    /**
     * 各系统公用工具类中的rnfs配置信息key前缀
     */
    public static final String CACHE_RNFSCFG_PREFIX = "CO_RNFS:";

    /**接入运营管理中心管理的JVM缓存Key值管理
     * 键值命名规则：系统名_业务名:自定义key
     * 由运营管理中心与业务应用在命名规则的基础上进行约自行约定
     * 此部分为上传下载公用工具类中的JVM缓存key管理
     */
    public interface UPDOWN_JVM {
        /**国政通图片上传下载时是否onest优先的开关*/
        String GZT_FILE_ONEST_SWITCH = CACHE_SWITCH_PREFIX + "GZT_FILE_ONEST_SWITCH";

        /**国政通图片使用rnfs上传时的超时时间配置*/
        String GZT_FILE_RNFS_TIME_OUT = CACHE_SWITCH_PREFIX + "GZT_FILE_RNFS_TIME_OUT";

        /**国政通图片上传下载分省路由配置，key=ACMS_SWITCH:GZT_FILE_SERVER_${身份证前6位，2位2位以下划线分割，如：41_10}，value=rnfs主机别名*/
        String GZT_FILE_SERVER_ = CACHE_SWITCH_PREFIX + "GZT_FILE_SERVER_";

        /**国政通图片上传下载默认rnfs主机配置*/
        String GZT_FILE_SERVER_DEFAULT = CACHE_SWITCH_PREFIX + "GZT_FILE_SERVER_DEFAULT";

        /**国政通图片上传下载分省路由配置，该省份上一次配置的rnfs主机，key=ACMS_SWITCH:GZT_FILE_LAST_SERVER_${身份证前6位，2位2位以下划线分割，如：41_10}，value=rnfs主机别名*/
        String GZT_FILE_LAST_SERVER_ = CACHE_SWITCH_PREFIX + "GZT_FILE_LAST_SERVER_";


        /** rnfs_cfg分组上传配置信息 */
        String GROUP_RNFS_CFG_PREFIX = CACHE_RNFSCFG_PREFIX;

        /** rnfs_cfg 根据url前缀获取ftp配置 例如：aFtp */
        String FTP_CFG_PREFIX = CACHE_RNFSCFG_PREFIX;

        /** rnfs_cfg分组上传配置信息 */
        String RNFS_USERNAME_PWD = CACHE_SWITCH_PREFIX + "RNFS_USERNAME_PASSWORD";

        /** rnfs_cfg分组上传配置信息 */
        String RNFS_TIME_OUT = CACHE_SWITCH_PREFIX + "RNFS_TIME_OUT";

        /** 业务图片上传 区分使用onest 或者 rnfs/ftp */
        String ONEST_UPDOWN_FILE_FALG = CACHE_SWITCH_PREFIX + "IS_ONEST_UPDOWN";

    }

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

    }

    /**
     * 接入运营管理中心管理的JVM缓存Key值管理
     * 键值命名规则：系统名_业务名:自定义key
     * 由运营管理中心与业务应用在命名规则的基础上进行约自行约定
     * 示例：
     *	调用国政通总放行开关，true打开放行，false关闭走实时接口
     *	ACMS_SWITCH:SEND_GZT_PASS_FLAG
     */
	public interface JVM {

		/** 是否进行手持证件照无证件的检测  */
		String PIC_CHECK_FETCH_ISHEAD = CACHE_SWITCH_PREFIX + "PIC_CHECK_FETCH_ISHEAD";
		/**  手持证件照判断是否含有证件照的分值 */
		String PIC_CHECK_FETCH_PER_SCORE = CACHE_SWITCH_PREFIX + "PIC_CHECK_FETCH_PER_SCORE";
		/**  自拍照是否有人像判定分值 */
		String PIC_CHECK_FETCH_PORTRAIT_SCORE = CACHE_SWITCH_PREFIX + "PIC_CHECK_FETCH_PORTRAIT_SCORE";
		/**  是否判断picZIn为HACK攻击照片 当不传或者0 的时候不进行防HACK的检测,当为1的时候进行防HACK的检测 */
		String PIC_CHECK_FETCH_HACK_SWITCH = CACHE_SWITCH_PREFIX + "PIC_CHECK_FETCH_HACK_SWITCH";
		/**  防HACK的检测的判定分值 */
		String PIC_CHECK_FETCH_HACK_SCORE = CACHE_SWITCH_PREFIX + "PIC_CHECK_FETCH_HACK_SCORE";
		/**  人像比对服务的url */
		String PIC_CHECK_FETCH_URL = CACHE_SWITCH_PREFIX + "PIC_CHECK_FETCH_URL";
		/**  人像比对服务的默认比分 */
		String PIC_CHECK_DEFAULT_VALUE = CACHE_SWITCH_PREFIX + "PIC_CHECK_DEFAULT_VALUE";
		/**  调用人像比对服务的超时时间 */
		String PIC_CHECK_FETCH_TIMEOUT = CACHE_SWITCH_PREFIX + "PIC_CHECK_FETCH_TIMEOUT";
		/** 活体检测分省控制开关 */
		String FACE_LIVE_BY_PROVCODE = CACHE_SWITCH_PREFIX + "FACE_LIVE_BY_PROVCODE_WORKORDER_NFCNEW";
		/** 活体检测默认分值 */
		String FACE_LIVE_DEFAULT_SCORE = CACHE_SWITCH_PREFIX + "FACE_LIVE_DEFAULT_SCORE";
		/** 活体检测服务器地址 */
		String WEB_FETCH_FACE_LIVE_URL = CACHE_SWITCH_PREFIX + "WEB_FETCH_FACE_LIVE_URL";
	}

}
