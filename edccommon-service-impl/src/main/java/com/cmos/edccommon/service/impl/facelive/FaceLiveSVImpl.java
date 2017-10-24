package com.cmos.edccommon.service.impl.facelive;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.facelive.CoFaceLiveInfoDO;
import com.cmos.edccommon.beans.piccompare.CoPicCompareInfoDO;
import com.cmos.edccommon.dao.facelive.CoFaceLiveInfoDAO;
import com.cmos.edccommon.iservice.facelive.IFaceLiveSV;
import com.cmos.edccommon.utils.JsonUtil;
import com.cmos.msg.common.MsgFMessage;
import com.cmos.msg.exception.MsgException;


/**
 * 静默活体服务
 *
 * @author xdx
 * 
 */
@Service(group = "edcco")
public class FaceLiveSVImpl implements IFaceLiveSV {
	Logger log=LoggerFactory.getActionLog(FaceLiveSVImpl.class);
	
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
		resultBean.setDetctnId(System.currentTimeMillis());//主键
		
		resultBean.setCrtUserId("test");
		Date nowTime = new Date();
		resultBean.setCrtTime(nowTime);
		resultBean.setCrtAppSysId("test");
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		String dateString = dateFormat.format(nowTime);
		resultBean.setSplitName(dateString);
		
		dao.insert(resultBean);
	}
	
	@Override
	public void process(MsgFMessage msg) throws MsgException {
		String msgContent = (String) msg.getMsg();
		log.info("**************************MQ消费进程开始执行"+msgContent);
		CoFaceLiveInfoDO logBean = (CoFaceLiveInfoDO) JsonUtil.convertJson2Object(msgContent,CoFaceLiveInfoDO.class);
		saveFaceLiveLog(logBean);
	}

}
