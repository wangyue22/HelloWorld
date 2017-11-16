package com.cmos.edccommon.service.impl.facelive;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.facelive.CoFaceLiveInfoDO;
import com.cmos.edccommon.dao.facelive.CoFaceLiveInfoDAO;
import com.cmos.edccommon.iservice.facelive.IFaceLiveSV;
import com.cmos.edccommon.utils.StringUtil;


/**
 * 静默活体服务
 *
 * @author xdx
 * 
 */
@Service(group = "edcco")
public class FaceLiveSVImpl implements IFaceLiveSV {
	Logger log = LoggerFactory.getActionLog(FaceLiveSVImpl.class);
	
	@Autowired
	private CoFaceLiveInfoDAO dao;

	/**
	 * 保存静默活体检测记录
	 * 
	 * @param param
	 * @return
	 * @date 2017-10-10 17:00:00
	 */
	public void saveFaceLiveLog(CoFaceLiveInfoDO resultBean) {
		Date nowTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		String dateString = dateFormat.format(nowTime);
		
		String idntifScore = resultBean.getIdntifScore();
		if (StringUtil.isNotBlank(idntifScore) && idntifScore.length() > 10) {
			resultBean.setIdntifScore(idntifScore.substring(0, 10));
		}
		resultBean.setCrtUserId("EDCCOMMON");
		resultBean.setModfTime(nowTime);
		if (resultBean.getCrtTime() == null) {
			resultBean.setCrtTime(nowTime);
		}
		resultBean.setCrtAppSysId("EDCCOMMON");
		resultBean.setSplitName(dateString);

		dao.insert(resultBean);
	}
}
