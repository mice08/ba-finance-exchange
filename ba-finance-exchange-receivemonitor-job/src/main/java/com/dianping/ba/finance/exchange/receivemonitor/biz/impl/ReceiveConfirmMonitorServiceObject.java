package com.dianping.ba.finance.exchange.receivemonitor.biz.impl;


import com.dianping.ba.finance.exchange.receivemonitor.api.ReceiveConfirmMonitorService;
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveConfirmMonitorData;
import com.dianping.ba.finance.exchange.receivemonitor.biz.dao.ReceiveConfirmMonitorDao;


/**
 *
 */
public class ReceiveConfirmMonitorServiceObject implements ReceiveConfirmMonitorService {

    private ReceiveConfirmMonitorDao receiveConfirmMonitorDao;



	@Override
	public ReceiveConfirmMonitorData loadReceiveConfirmData(int roId) {
		return receiveConfirmMonitorDao.loadReceiveConfirmData(roId);
	}

	public void setReceiveConfirmMonitorDao(ReceiveConfirmMonitorDao receiveConfirmMonitorDao) {
		this.receiveConfirmMonitorDao = receiveConfirmMonitorDao;
	}
}
