package com.cmos.edccommon.service.impl.piccompare;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.piccompare.CoPicCompareInfoDO;
import com.cmos.edccommon.dao.piccompare.CoPicCompareInfoDAO;
import com.cmos.edccommon.iservice.piccompare.IPicCompareSV;
import com.cmos.edccommon.utils.StringUtil;
import com.cmos.edccommon.utils.consts.AppCodeConsts;

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
	 * MQ保存人像比对记录(若使用vertica，可废弃)
	 * 
	 * @param param
	 * @return
	 * @date 2017-10-10 17:00:00
	 */
	public void savePicCompareLog(CoPicCompareInfoDO resultBean) {

		Date nowTime = new Date();
		if (StringUtil.isBlank(resultBean.getCrtAppSysId())) {
			resultBean.setCrtAppSysId(AppCodeConsts.APP_SYS_ID.EDC_COMMON);
		}
		if (StringUtil.isBlank(resultBean.getCrtUserId())) {
			resultBean.setCrtUserId(AppCodeConsts.APP_USER_ID.UNDEFINED);
		}
		
		if (resultBean.getCrtTime() == null) {
			log.error("创建时间为空，请及时查看");
			resultBean.setCrtTime(nowTime);
		}
		resultBean.setModfTime(nowTime);
		dao.insert(resultBean);
	}
}
