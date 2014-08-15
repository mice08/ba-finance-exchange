package com.dianping.ba.finance.exchange.receivemonitor.biz.impl;


import com.dianping.ba.finance.exchange.receivemonitor.api.ReceiveOrderMonitorService;
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveOrderMonitorData;
import com.dianping.ba.finance.exchange.receivemonitor.biz.dao.ReceiveOrderMonitorDao;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: noahshen
 * Date: 14-3-26
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public class ReceiveOrderMonitorServiceObject implements ReceiveOrderMonitorService {

    private ReceiveOrderMonitorDao receiveOrderMonitorDao;


	@Override
	public List<ReceiveOrderMonitorData> findReceiveOrderData(Date startDate, Date endDate) {
		return receiveOrderMonitorDao.findReceiveOrderData(startDate,endDate);
	}

	@Override
	public ReceiveOrderMonitorData loadReceiveOrderData(int roId) {
		return receiveOrderMonitorDao.loadReceiveOrderData(roId);
	}

	public void setReceiveOrderMonitorDao(ReceiveOrderMonitorDao receiveOrderMonitorDao) {
		this.receiveOrderMonitorDao = receiveOrderMonitorDao;
	}
}
