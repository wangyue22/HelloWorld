package com.cmos.edccommon.utils.consts;

/**
 * 查询秘钥入参
 * 常量类
 */
public final class KeyInfoConstants {

	/**
	 * 查询秘钥的固定前缀和分隔符
	 * @author xdx
	 *
	 */
	public interface CACHEKEY {
		/** RSA秘钥缓存开关头 */
		String CO_RSAKEY_PREFIX = "CO_RSAKEY:";
		
		/** REALACC表中的DES秘钥的缓存开关前缀  */
		String CO_REALACC_PREFIX  = "CO_REALACC:";
		
		/** 缓存开关配置的DES秘钥的缓存开关前缀 */
		String CACHE_SWITCH_PREFIX  = "CO_SWITCH:DES_";
		
		/** 缓存开关头分割符*/
		String SEPARATOR = "_";
	}
}