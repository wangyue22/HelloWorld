package com.cmos.edccommon.iservice.piccompare;

import com.cmos.edccommon.beans.common.InputObject;
import com.cmos.edccommon.beans.common.OutputObject;
import com.cmos.msg.exception.MsgException;

/**
 * 人像比对
 *
 * @author xdx
 * @since 1.0
 */

public interface IPicCompareSV {

   
    /**
     * just test!
     * 
     * @param param
     * @return
     * @date 2017-10-10 17:00:00
     */
    String picCompareTest(InputObject param);
    
    /**
     * 人像比对
     * 
     * @param param
     * @return
     * @date 2017-10-12 17:00:00
     */
    OutputObject picCheck(InputObject param);
    
    /**
     * 人像比对判定
     * 
     * @param param
     * @return
     * @date 2017-10-12 17:00:00
     */
    OutputObject picCompare(InputObject param);
    
    
    void consumerMQ() throws MsgException;
}
