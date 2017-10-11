package com.cmos.edccommon.service.impl.piccompare;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.edccommon.beans.common.InputObject;
import com.cmos.edccommon.beans.piccompare.CoPicCompareInfoDO;
import com.cmos.edccommon.dao.piccompare.CoPicCompareInfoDAO;
import com.cmos.edccommon.iservice.piccompare.IPicCompareSV;

/**
 * 人像比对
 *
 * @author xdx
 * 
 */
@Service(group = "edcco")
public  class PicCompareSVImpl implements  IPicCompareSV {

	@Autowired
	private CoPicCompareInfoDAO dao;
	 
    /**
     * just test!
     * @param param
     * @return
     * @date 2017-10-10 17:00:00
     */
    public String picCompare(InputObject param){
    	
		CoPicCompareInfoDO resultBean =new CoPicCompareInfoDO();
		resultBean.setCmprId(4l);
		resultBean.setCrtUserId("test");
		Date nowTime = new Date(System.currentTimeMillis());
		resultBean.setCrtTime(nowTime);
		resultBean.setCrtAppSysId("test");
		int result = dao.insert(resultBean);
    	
    	
		return result+"";

    }
}
