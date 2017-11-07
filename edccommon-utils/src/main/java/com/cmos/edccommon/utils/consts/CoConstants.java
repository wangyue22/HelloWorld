package com.cmos.edccommon.utils.consts;

/**
 * 常量类
 */
public final class CoConstants {


    //存放密钥类型 
    public interface CR_KEY_TYPE {
		String DES="DES";
		String AES="AES";
		String RSA="RSA";
    }
    
    //存放业务类型
    public interface BIZ_TYPE{
		String KSJ="KSJ";
		String DEFAULT="DEFAULT";
    }
   
    //存放人像比对图片类型
    public interface PIC_TYPE{
    	/**
    	 * 国政通头像
    	 */
		String PIC_GZT="g";
		/**
    	 * 芯片头像
    	 */
		String PIC_STOIN="x";
		/**
    	 * 手持证件照人像
    	 */
		String PHOTO_R="r";
		/**
    	 * 非手持证件照人像
    	 */
		String PHOTO_T="t";
    }
}