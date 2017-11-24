package com.cmos.edccommon.utils.consts;

public class AppCodeConsts {
    /**
     * 定义各个业务系统id
     * 用于存储表中crtSysId,mdfSysId
     */
    public interface APP_SYS_ID {
        //客户端管理
        String EDC_CM = "0001";

        //选号开户
        String EDC_OACC = "0002";

        //短信中心
        String EDC_SMS = "0003";

        //通用能力
        String EDC_COMMON = "0004";

        //接口适配层
        String EDC_ADAPI = "0005";

        //认证服务中心
        String EDC_CTSVS = "0006";

        //身份认证中心
        String EDC_ACMS = "0007";

        //运营中心
        String EDC_OPMS = "0008";
        
        //未定义
        String UNDEFINED = "UNDEFINED";
    }

    /**
     * 定义各个业务系统程序用户id
     * 用于存储表中crtUserId,mdfUserId
     */
    public interface APP_USER_ID{
        String BUSI_TASK_USER = "T001";
        String BUSI_MQ_USER = "MQ001";
        //未定义
        String UNDEFINED = "UNDEFINED";
    }
}
