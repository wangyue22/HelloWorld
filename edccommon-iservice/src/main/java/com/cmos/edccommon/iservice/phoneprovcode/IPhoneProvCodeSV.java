package com.cmos.edccommon.iservice.phoneprovcode;
/**
 * 根据手机号得到对应的省端编码
 *
 * @author 赵虎
 *
 */

public interface IPhoneProvCodeSV {

   
    /**
     * 
     * 
     * @param param
     * @return
     * @date 2017-10-10 17:00:00
     */
  public  String getProvCodeByPhoneNum(String phoneNum);
}