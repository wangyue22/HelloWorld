package com.cmos.edccommon.utils.consts;

public class KafkaConsts {
    /**
     * 应用TOPIC列表
     * 需要提前找运维申请建立
     */
    public interface TOPIC {

        // vertica topic
    	//人像比对信息表
    	String CO_PIC_COMPARE_INFO = "TOPIC_CO_PIC_COMPARE_INFO";
    	//活体检测信息表
        String CO_FACE_LIVE_INFO = "TOPIC_CO_FACE_LIVE_INFO";

    }
}
