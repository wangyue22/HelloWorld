package com.cmos.edccommon.utils.consts;

/**
 * 常量类
 */
public final class MqConstants {
    //MQ的主题
	public interface MQ_TOPIC {
		/** 异步保存人像比对 */
		String PIC_COMPARE = "EDCCO_PICCOMPARE";
		/** 异步保存静默活体 */
		String FACE_LIVE = "EDCCO_FACELIVE";
	}
}