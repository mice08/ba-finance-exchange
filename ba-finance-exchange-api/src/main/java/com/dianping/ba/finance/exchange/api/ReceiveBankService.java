package com.dianping.ba.finance.exchange.api;


import com.dianping.ba.finance.exchange.api.datas.ReceiveBankData;

import java.util.List;

/**
 *  收款银行的Service类
 */
public interface ReceiveBankService {
    /**
     * 获取所有收款银行
     * @return
     */
    List<ReceiveBankData> findAllReceiveBank();

    /**
     * 清除缓存
     */
    void clearCache();
}
