package com.dianping.ba.finance.exchange.api;


import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyRecordData;

/**
 * Created by Administrator on 2014/7/23.
 */
public interface ReceiveNotifyRecordService {
    /**
     * 保存收款请求记录
     *
     * @param receiveNotifyRecordData
     * @return
     */
    int insertReceiveNotifyRecord(ReceiveNotifyRecordData receiveNotifyRecordData);
}
