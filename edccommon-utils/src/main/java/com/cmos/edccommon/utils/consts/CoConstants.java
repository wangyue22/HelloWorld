package com.cmos.edccommon.utils.consts;

/**
 * 常量类
 */
public final class CoConstants {

	// 存放密钥类型
	public interface CR_KEY_TYPE {
		String DES = "DES";
		String AES = "AES";
		String RSA = "RSA";
	}

	// 存放业务类型
	public interface BIZ_TYPE {
		String KSJ = "KSJ";
		String DEFAULT = "DEFAULT";
	}

	// 存放人像比对图片类型
	public interface PIC_TYPE {
		/**
		 * 国政通头像
		 */
		String PIC_GZT = "g";
		/**
		 * 芯片头像
		 */
		String PIC_STOIN = "x";
		/**
		 * 手持证件照人像
		 */
		String PHOTO_R = "r";
		/**
		 * 非手持证件照人像
		 */
		String PHOTO_T = "t";
	}

	/**
	 * 数据表名常量类
	 */
	public interface DB_NAME {
		/** 人像比对 */
		String PIC_COMPARE = "T_CO_PIC_COMPARE_INFO";
		/** 静默活体 */
		String FACE_LIVE = "T_CO_FACE_LIVE_INFO";
	}
	
	/**
	 * 返回码约定值
	 */
	public interface RESULT_TYPE {
		/**调用外部服务发生了异常*/
		String EXCEPTION_CODE = "-98"; 
		/**调用外部服务服务的初始值，未调用外部服务，则在数据库存放该值*/
		String INIT_CODE = "-99";		
	}

}