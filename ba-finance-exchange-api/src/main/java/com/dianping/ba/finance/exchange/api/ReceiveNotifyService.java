package com.dianping.ba.finance.exchange.api;

import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus;

import java.util.List;

/**
 * Created by Administrator on 2014/7/24.
 */
public interface ReceiveNotifyService {
    /**
     * 保存收款通知
     *
     * @param receiveNotifyData
     * @return
     */
    int insertReceiveNotify(ReceiveNotifyData receiveNotifyData);

    /**
     * 更新收款通知匹配信息
     * @param setStatus
     * @param roId
     * @param preStatus
     * @param rnId
     * @return
     */
    boolean updateReceiveNotifyMatchId(int setStatus, int roId, int preStatus, int rnId);

    /**
     *
     * @param status
     * @return
     */
    List<ReceiveNotifyData> getUnMatchedReceiveNotify(ReceiveNotifyStatus status);

}
