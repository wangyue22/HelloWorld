package com.cmos.edccommon.service.impl.piccompare;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		String dateString = dateFormat.format(nowTime);
		Long cmprId = resultBean.getCmprId();
		String uniqueSequence;
		if (cmprId == null) {
			uniqueSequence = System.currentTimeMillis() + getRandomCode(5);
			log.error("生成主键为空，使用默认主键:" + uniqueSequence);
			if (uniqueSequence.length() > 18) {
				uniqueSequence = uniqueSequence.substring(uniqueSequence.length() - 18, uniqueSequence.length());

			}
			cmprId = Long.parseLong(uniqueSequence);
			resultBean.setCmprId(cmprId);
		}

		resultBean.setCrtUserId("edccommon");
		resultBean.setCrtAppSysId("edccommon");

		if (resultBean.getCrtTime() == null) {
			resultBean.setCrtTime(nowTime);
		}
		resultBean.setModfTime(nowTime);
		resultBean.setSplitName(dateString);
		dao.insert(resultBean);
	}

	private String getRandomCode(int num) {
		// 创建一个随机数生成器类
		Random random = new Random();
		StringBuilder randomCode = new StringBuilder();
		// 设置验证码字数
		int codeCount = num;
		// 设置验证码内容
		char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		// 随机产生codeCount数字的验证码。
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字。
			String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
			// 将产生的六个随机数组合在一起。
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}
}
