package com.cmos.edccommon.service.impl.piccompare;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
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
public class PicCompareSVImpl implements IPicCompareSV {
	Logger log=LoggerFactory.getActionLog(PicCompareSVImpl.class);
	
//	@Autowired
//	LocalTransactionExecuter excutor;

	@Autowired
	private CoPicCompareInfoDAO dao;

	/**
	 * just test!
	 * 
	 * @param param
	 * @return
	 * @date 2017-10-10 17:00:00
	 */
	private String savePicCompareLog(InputObject param) {
		CoPicCompareInfoDO resultBean = new CoPicCompareInfoDO();
		resultBean.setCmprId(System.currentTimeMillis());
		resultBean.setCrtUserId("test");
		Date nowTime = new Date(System.currentTimeMillis());
		resultBean.setCrtTime(nowTime);
		resultBean.setCrtAppSysId("test");
		resultBean.setSplitName("201710");
		int result = dao.insert(resultBean);
		return result + "";
	}

	/**
	 * just test!
	 * 
	 * @param inParam
	 * @return
	 * @date 2017-10-10 17:00:00
	 */
	public String picCompareTest(InputObject inParam) {
		savePicCompareLog(inParam);
		return savePicCompareLog(inParam);
	}	

}
