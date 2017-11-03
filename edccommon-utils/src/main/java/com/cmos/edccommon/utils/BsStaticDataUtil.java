package com.cmos.edccommon.utils;

/**
 * 缓存操作工具类
 * @since 2017/10/11
 */
public class BsStaticDataUtil {

	/**
	 * 根据key和name取value 
	 * fromType指是从jvm中获取还是从redis中获取
	 * @param codeType
	 * @param codeName
	 * @param fromType
	 * @return
	 */
	public static String getCodeValue(String codeType, String codeName, String fromType){
		//TODO 
		//1、先从jvm中获取
		//2、如果jvm中为空，再从redis中获取
		//3、如果redis中为空，则从DB获取并插入到redis中和jvm中。
		
		
//		IStaticDataNewSV staticDataNewSV;
		String dataValues[] = {};
//		= staticDataNewSV.getStaticDataFromCache(codeType);
		if (null != dataValues && dataValues.length > 0) {
			for (int i = 0; i < dataValues.length; i++) {
				String value = dataValues[i];
				if (value.equals(codeName))
					return value;
			}
		}
		return "null";
	}
	/**
	 * 项目启动调用，加载缓存到redis中
	 * @param 
	 */
	public static void initRedis(){
		//TODO
	}
	/**
	 * 项目启动调用，加载缓存到jvm中
	 * @param 
	 */
	public static void initJVM(){
		//TODO
	}
	
	/**
	 * 运营平台调用服务，更新JVM中指定key和value
	 * @param codeType
	 * @param codeName
	 * @param value
	 * @param state
	 */
	public static void updateJVM(String codeType, String codeName, String value, String state){
		//TODO
	}
	/**
	 * 运营平台调用服务，更新DB中指定key和value
	 * @param codeType
	 * @param codeName
	 * @param value
	 * @param state
	 */
	public static void updateDB(String codeType, String codeName, String value, String state){
		//TODO
	}
	/**
	 * 运营平台调用服务，更新Redis指定key和value
	 * @param codeType
	 * @param codeName
	 * @param value
	 * @param state
	 */
	public static void updateRedis(String codeType, String codeName, String value, String state){
		//TODO
	}
	
	public static void main(String[] args) {
		String value = getCodeValue("","","");
		System.out.println("value："+value);
	}
	public static String getCodeName(String string, String code, String string2) {
		// TODO Auto-generated method stub
		return null;
	}

}
