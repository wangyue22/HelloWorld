package com.cmos.edccommon.service.impl.mq;

import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;
public class LocalTransactionExecuter {
		
		public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg1)        
          {
			try {
				System.out.println("充值扣款100元成功");
				System.out.println("手机充值100元成功");
			} catch (Exception e) {
				return LocalTransactionState.ROLLBACK_MESSAGE;
			}
			return LocalTransactionState.COMMIT_MESSAGE;
		}
	};