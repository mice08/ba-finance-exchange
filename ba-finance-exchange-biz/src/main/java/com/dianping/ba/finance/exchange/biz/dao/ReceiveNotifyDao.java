package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.beans.ReceiveNotifySearchBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.core.type.PageModel;

import java.util.List;

/**
 * Created by Administrator on 2014/7/24.
 */
public interface ReceiveNotifyDao extends GenericDao {
    /**
     * 添加收款通知记录
     * @param receiveNotifyData
     * @return
     */
    @DAOAction(action = DAOActionType.INSERT)
    int insertReceiveNotify(@DAOParam("receiveNotifyData")ReceiveNotifyData receiveNotifyData);

    @DAOAction(action = DAOActionType.UPDATE)
    int updateReceiveNotifyMatchId(@DAOParam("setStatus") int setStatus,
                                   @DAOParam("roId") int roId,
                                   @DAOParam("preStatus") int preStatus,
                                   @DAOParam("rnId") int rnId);


    @DAOAction(action = DAOActionType.QUERY)
    List<ReceiveNotifyData> getUnMatchedReceiveNotify(@DAOParam("status") int status);


    @DAOAction(action = DAOActionType.QUERY)
    List<ReceiveNotifyData> findUnmatchedLeftReceiveNotify(@DAOParam("status") int status,
                                                           @DAOParam("roId") int roId,
                                                           @DAOParam("excludeApplicationId") String excludeApplicationId);

    @DAOAction(action = DAOActionType.UPDATE)
    int clearReceiveNotifyMatchInfo(@DAOParam("status") int status, @DAOParam("rnIdList") List<Integer> rnIdList);


    @DAOAction(action = DAOActionType.LOAD)
    ReceiveNotifyData loadUnmatchedReceiveNotifyByApplicationId(@DAOParam("status") int status,
                                                                @DAOParam("businessType") int businessType,
                                                                @DAOParam("applicationId") String applicationId);

    @DAOAction(action = DAOActionType.QUERY)
    List<ReceiveNotifyData> findMatchedReceiveNotify(@DAOParam("status") int status,
                                                     @DAOParam("roId") int roId);

    @DAOAction(action = DAOActionType.UPDATE)
    int removeReceiveNotifyMatchRelation(@DAOParam("rnId") int rnId,
                                                     @DAOParam("roId") int roId,
                                                     @DAOParam("status") int status);


    @DAOAction(action = DAOActionType.LOAD)
    ReceiveNotifyData loadMatchedReceiveNotify(@DAOParam("status") int status,
                                               @DAOParam("rnId") int rnId,
                                               @DAOParam("roId") int roId);

    @DAOAction(action = DAOActionType.PAGE)
    PageModel paginateReceiveNotifyList(@DAOParam("receiveNotifySearchBean") ReceiveNotifySearchBean receiveNotifySearchBean,
                                        @DAOParam("page") int page,
                                        @DAOParam("max") int max);


    @DAOAction(action = DAOActionType.UPDATE)
    int updateReceiveNotifyConfirm(@DAOParam("setStatus") int setStatus,
                                   @DAOParam("preStatus") int preStatus,
                                   @DAOParam("roId") int roId,
                                   @DAOParam("rnId") int rnId);


}

