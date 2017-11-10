package com.cmos.edccommon.utils.consts;

/**
 * 查询秘钥入参
 * 常量类
 */
public final class KeyInfoConstants {
    
	public interface CACHEKEY {
		/** RSA秘钥缓存开关头 */
		String CO_RSAKEY_PREFIX = "CO_RSAKEY:";
		/** DES秘钥缓存开关头 */
		String CO_REALACC_PREFIX  = "CO_REALACC:";
		
		/** 缓存开关头分割符*/
		String SEPARATOR = "_";
	}
}