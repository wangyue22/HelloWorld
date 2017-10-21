package com.cmos.edccommon.web.mqconsumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cmos.edccommon.beans.piccompare.CoPicCompareInfoDO;
import com.cmos.edccommon.iservice.piccompare.IPicCompareSV;
import com.cmos.edccommon.utils.JsonUtil;
import com.cmos.msg.common.IConsumerProcessor;
import com.cmos.msg.common.MsgFMessage;
import com.cmos.msg.exception.MsgException;

public class MsgConsumerImpl implements IConsumerProcessor {

	@Reference(group = "edcco")
	private IPicCompareSV picCompareSV;

	@Override
	public void process(MsgFMessage msg) throws MsgException {
		String msgContent = (String) msg.getMsg();
		CoPicCompareInfoDO logBean = (CoPicCompareInfoDO) JsonUtil.convertJson2Object(msgContent,CoPicCompareInfoDO.class);
		picCompareSV.savePicCompareLog(logBean);
	}
}
