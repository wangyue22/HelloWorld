package com.cmos.edccommon.utils.des;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;

import com.cmos.common.exception.GeneralException;

/**
 * @字符串的加密解密，主要用于参数传递
 */
public class MsDesPlus {
	private static String strDefaultKey = "RNQDK";
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
	public MsDesPlus() throws GeneralException {
		this(strDefaultKey);
	}

	/**
	 * 指定密钥构造方法
	 * 
	 * @param strKey
	 *            指定的密钥
	 * @throws GeneralException
	 */
	public MsDesPlus(String strKey) throws GeneralException {
		try {
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			Key key = getKey(strKey.getBytes());
			
			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			
			decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
			throw new GeneralException("", e);
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
			throw new GeneralException("", e);
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
		return byteArrToHexStr(encrypt(strIn.getBytes()));
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
			throw new GeneralException("", e);
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
			return new String(decrypt(hexStrToByteArr(strIn)), "utf-8");
		} catch (Exception e) {
			throw new GeneralException("", e);
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
			throw new GeneralException("", e);
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

	
	public static void main(String[] args) throws GeneralException {
//		MsDesPlus desPlus = new MsDesPlus();
//		String txt1 = desPlus.encrypt("");
//		//System.out.println(txt1);
//		txt1="103cae2055b3e7faa75aa55ae9e8e0ebc7c5324727f78af2d1a8f3bda968ffd5bf92c1902ee2a8a7fef1be35956b32524acdca67257b6f1b943faa4b8a489deeb3fe2f1829a2331593b21d331a2acc4d62a3ae3a4d3832d0ca5188e29b10b839f636c86cef5f8de78ba14cfea3a2a5acf513eafa1ae6bfc2de3833acf24967da";
//		//System.out.println(desPlus.decrypt(txt1));
	}
}
