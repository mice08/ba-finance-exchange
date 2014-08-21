package com.dianping.ba.finance.exchange.voucher.job;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;

import java.util.Calendar;
import java.util.Date;


public class NoContractReceiveVoucherController {
    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.voucher.job.NoContractReceiveVoucherController");

	public boolean execute() {
		monitorLogger.info("Start to monitor....");
		Date currentMonitorTime = Calendar.getInstance().getTime();
		long startTime = System.currentTimeMillis();

        return true;
	}

}
