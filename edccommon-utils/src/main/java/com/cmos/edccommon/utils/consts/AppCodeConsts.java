package com.cmos.edccommon.utils.consts;

public class AppCodeConsts {
    /**
     * 定义各个业务系统id
     * 用于存储表中crtSysId,mdfSysId
     */
    public interface APP_SYS_ID {
        //客户端管理
        String EDC_CM = "CM";

        //选号开户
        String EDC_OACC = "OACC";

        //短信中心
        String EDC_SMS = "SMS";

        //通用能力
        String EDC_COMMON = "CO";

        //接口适配层
        String EDC_ADAPI = "ADAPI";

        //认证服务中心
        String EDC_CTSVS = "CT";

        //身份认证中心
        String EDC_ACMS = "ACMS";

        //运营中心
        String EDC_OPMS = "OP";

        //未定义
        String UNDEFINED = "UNDEFINED";
    }

    /**
     * 定义各个业务系统程序用户id
     * 用于存储表中crtUserId,mdfUserId
     */
    public interface APP_USER_ID{
        String BUSI_TASK_USER = "TASK_";
        String BUSI_MQ_USER = "MQ_";
        // 未定义
        String UNDEFINED = "UNDEFINED";
    }
}
