package com.dianping.ba.finance.exchange.receivemonitor.api;


import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveConfirmMonitorData;
import com.dianping.ba.finance.exchange.receivemonitor.api.datas.ReceiveOrderMonitorData;

import java.util.Date;
import java.util.List;

/**
 * 查询收款确认的service接口
 */
public interface ReceiveConfirmMonitorService {


    /**
     *  根据收款单的id去获取收款确认信息
     * @param roId
     * @return
     */
    ReceiveConfirmMonitorData loadReceiveConfirmData(int roId);

}
