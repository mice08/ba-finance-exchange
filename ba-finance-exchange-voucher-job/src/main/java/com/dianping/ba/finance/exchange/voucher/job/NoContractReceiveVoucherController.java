package com.dianping.ba.finance.exchange.voucher.job;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ReceiveVoucherService;
import com.dianping.finance.common.util.DateUtils;
import com.dianping.finance.common.util.LionConfigUtils;
import com.site.lookup.util.StringUtils;

import java.util.Date;


public class NoContractReceiveVoucherController {
    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.voucher.job.NoContractReceiveVoucherController");
    private ReceiveVoucherService receiveVoucherService;

	public boolean execute() {
        MONITOR_LOGGER.info(String.format("NoContractReceiveVoucherController.execute start"));
        try{
            //当前日期减一天
            String strRunDate=DateUtils.format(DateUtils.addDate(DateUtils.getCurrentDate(),-1));
            String lionDate=LionConfigUtils.getProperty("ba-finance-exchange-voucher-job.runDate","");
            if (StringUtils.isNotEmpty(lionDate)) {
                strRunDate=lionDate;
            }
            Date runDate= DateUtils.formatDate(strRunDate);
            receiveVoucherService.generateUnconfirmedReceiveVoucher(runDate);
        }catch (Exception e){
            MONITOR_LOGGER.error(String.format("severity=[2] NoContractReceiveVoucherController.execute error"), e);
        }
        return true;
	}

    public void setReceiveVoucherService(ReceiveVoucherService receiveVoucherService) {
        this.receiveVoucherService = receiveVoucherService;
    }
}
