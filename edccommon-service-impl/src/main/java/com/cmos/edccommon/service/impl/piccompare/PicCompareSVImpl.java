package com.cmos.edccommon.service.impl.piccompare;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cmos.consumer.client.MsgConsumerClient;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.edccommon.beans.piccompare.CoPicCompareInfoDO;
import com.cmos.edccommon.dao.piccompare.CoPicCompareInfoDAO;
import com.cmos.edccommon.iservice.piccompare.IPicCompareSV;
import com.cmos.edccommon.utils.JsonUtil;
import com.cmos.msg.common.MsgFMessage;
import com.cmos.msg.exception.MsgException;


/**
 * 人像比对
 *
 * @author xdx
 * 
 */
@Service(group = "edcco")
public class PicCompareSVImpl implements IPicCompareSV {
	Logger log=LoggerFactory.getActionLog(PicCompareSVImpl.class);
	
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
		resultBean.setCmprId(System.currentTimeMillis());
		resultBean.setCrtUserId("test");
		resultBean.setCrtAppSysId("test");
		Date nowTime = new Date();
		resultBean.setCrtTime(nowTime);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		String dateString = dateFormat.format(nowTime);
		resultBean.setSplitName(dateString);
		dao.insert(resultBean);
	}

	/**
	 * MQ消费进程
	 */
	@Override
	public void process(MsgFMessage msg) throws MsgException {
		String msgContent = (String) msg.getMsg();
		log.info("**************************MQ消费进程开始执行"+msg);
		CoPicCompareInfoDO logBean = (CoPicCompareInfoDO) JsonUtil.convertJson2Object(msgContent,CoPicCompareInfoDO.class);
		savePicCompareLog(logBean);
	}
}
