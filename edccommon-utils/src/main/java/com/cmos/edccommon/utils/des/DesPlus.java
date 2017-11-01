package com.cmos.edccommon.utils.des;



import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;

import com.cmos.common.exception.GeneralException;



/**
 * @字符串的加密解密，主要用于参数传递
 */
public class DesPlus {
	private static String strDefaultKey = "";
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws GeneralException
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	private String byteArrToHexStr(byte[] arrB) throws GeneralException {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuilder sb = new StringBuilder(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 * 
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws GeneralException
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	private byte[] hexStrToByteArr(String strIn) throws GeneralException {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 默认构造方法，使用默认密钥
	 * 
	 * @throws GeneralException
	 */
	public DesPlus() throws GeneralException {
		this(strDefaultKey);
	}

	/**
	 * 指定密钥构造方法
	 * 
	 * @param strKey
	 *            指定的密钥
	 * @throws GeneralException
	 */
	public DesPlus(String strKey) throws GeneralException {
		try {
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			Key key = getKey(strKey.getBytes());
			
			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			
			decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
			throw new GeneralException("",e);
		}
	}

	/**
	 * 加密字节数组
	 * 
	 * @param arrB
	 *            需加密的字节数组
	 * @return 加密后的字节数组
	 * @throws GeneralException
	 */
	private byte[] encrypt(byte[] arrB) throws GeneralException {
		try {
			return encryptCipher.doFinal(arrB);
		} catch (Exception e) {
			throw new GeneralException("",e);
		}
	}

	/**
	 * 加密字符串
	 * 
	 * @param strIn
	 *            需加密的字符串
	 * @return 加密后的字符串
	 * @throws GeneralException
	 */
	public String encrypt(String strIn) throws GeneralException {
		try {
			return byteArrToHexStr(encrypt(strIn.getBytes("UTF-8")));
		} catch (Exception e) {
			throw new GeneralException("",e);
		}
	}

	/**
	 * 解密字节数组
	 * 
	 * @param arrB
	 *            需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws GeneralException
	 */
	private byte[] decrypt(byte[] arrB) throws GeneralException {
		try {
			return decryptCipher.doFinal(arrB);
		} catch (Exception e) {
			throw new GeneralException("",e);
		}
	}

	/**
	 * 解密字符串
	 * 
	 * @param strIn
	 *            需解密的字符串
	 * @return 解密后的字符串,以utf-8格式编码
	 * @throws GeneralException
	 */
	public String decrypt(String strIn) throws GeneralException {
		try {
			return new String(decrypt(hexStrToByteArr(strIn)), "UTF-8");
		} catch (Exception e) {
			throw new GeneralException("",e);
		}
	}

	/**
	 * @param strIn
	 *            需解密的字符串
	 * @param encode
	 *            解密后的字符编码
	 * @return
	 * @throws GeneralException
	 */
	public String decrypt(String strIn, String encode) throws GeneralException {
		try {
			return new String(decrypt(hexStrToByteArr(strIn)), encode);
		} catch (Exception e) {
			throw new GeneralException("",e);
		}
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws java.lang.Exception
	 */
	private Key getKey(byte[] arrBTmp) throws GeneralException {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
		return key;
	}

	//API加密报文解密---DesPlus("加密key")----desPlus.decrypt(加密报文（用户名密码等）
	public static void main(String[] args) throws GeneralException {
//		DesPlus desPlus = new DesPlus("HSTYW");
		//String ss=desPlus.decrypt("7fabab9ca52a5f5b99eab69fae3a251e154e6d1f1ee093adeed9495986e6d34340dc162a3dc9dec6266ed008b869f62b519716ab67f6d260868657e7c94d9c5d631ca22ad91323817cc7ad2d30ccde7772da144bc84f2c3a7e46f995bb92a65bb3e9493b50d7964420b794a8b9519c18d090889b3149efc0d8a96a0c79196d91aeea8dc41677aacc5ea8bc7733d10d053cfd78338d12dd577122b2b3d9fa2a691f537e4bb07ff7cf358bbac76a3ddf2cc38c185e0ab8794f9b03ea3a110011b8cfb735d9bc3889406d2a1c4efafcdc1f294c9f4a8fa671fc29061ee8986abed96eb020e889a42b60bf0eecc9daf6cc61ec0dbabc4475b968f29448009b5d30151f67d1227899586003680da809cbce1435f8430c9fd1e29b812a37773003e8dded5bc0ee78b64355a616969e567ba67f");
		//String ss1=new String(ss.getBytes("GBK"));
		//System.out.println(ss);
	}
}
