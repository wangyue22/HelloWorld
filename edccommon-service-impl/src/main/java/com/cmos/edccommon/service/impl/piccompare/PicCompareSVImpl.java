package com.cmos.edccommon.service.impl.piccompare;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
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
	Logger log = LoggerFactory.getActionLog(PicCompareSVImpl.class);

	@Autowired
	private CoPicCompareInfoDAO dao;

	/**
	 * 保存人像比对记录
	 * 
	 * @param param
	 * @return
	 * @date 2017-10-10 17:00:00
	 */
	public void savePicCompareLog(CoPicCompareInfoDO resultBean) {

		Date nowTime = new Date();
		resultBean.setCrtUserId("EDCCOMMON");
		resultBean.setCrtAppSysId("EDCCOMMON");

		if (resultBean.getCrtTime() == null) {
			log.error("创建时间为空，请及时查看");
			resultBean.setCrtTime(nowTime);
		}
		resultBean.setModfTime(nowTime);
		dao.insert(resultBean);
	}
}
