package com.dianping.ba.finance.exchange.api;

import com.dianping.ba.finance.exchange.api.beans.ReceiveNotifySearchBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus;
import com.dianping.core.type.PageModel;

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

    /**
     *
     * @param status
     * @param excludeApplicationId
     * @return
     */
    List<ReceiveNotifyData> findUnmatchedLeftReceiveNotify(ReceiveNotifyStatus status, String excludeApplicationId);

    /**
     * 清除未匹配上的收款通知的匹配信息
     * @param status
     * @param rnIdList
     * @return
     */
    int clearReceiveNotifyMatchInfo(ReceiveNotifyStatus status, List<Integer> rnIdList);

    /**
     * 根据appliactionId，获取未匹配的收款通知
     * @param status
     * @param applicationId
     * @return
     */
    ReceiveNotifyData loadUnmatchedReceiveNotifyByApplicationId(ReceiveNotifyStatus status, int businessType, String applicationId);

     /**
     * 分页获取收款通知列表
     * @param receiveNotifySearchBean
     * @param page
     * @param max
     * @return
     */
    PageModel paginateReceiveNotifyList(ReceiveNotifySearchBean receiveNotifySearchBean, int page, int max);
}
