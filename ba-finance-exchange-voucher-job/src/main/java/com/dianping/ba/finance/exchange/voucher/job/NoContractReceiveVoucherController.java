package com.dianping.ba.finance.exchange.voucher.job;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ReceiveVoucherService;

import java.util.Date;


public class NoContractReceiveVoucherController {
    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.voucher.job.NoContractReceiveVoucherController");
    private ReceiveVoucherService receiveVoucherService;

	public boolean execute() {
        MONITOR_LOGGER.info(String.format("NoContractReceiveVoucherController.execute start"));
        try{
            receiveVoucherService.generateUnconfirmedReceiveVoucher(new Date());
        }catch (Exception e){
            MONITOR_LOGGER.error(String.format("severity=[2] NoContractReceiveVoucherController.execute error"), e);
        }
        return true;
	}

    public void setReceiveVoucherService(ReceiveVoucherService receiveVoucherService) {
        this.receiveVoucherService = receiveVoucherService;
    }
}
