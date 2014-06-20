package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountFlowData;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 上午10:32
 * To change this template use File | Settings | File Templates.
 */
public interface ShopFundAccountFlowDao extends GenericDao {

    @DAOAction(action = DAOActionType.INSERT)
    int insertShopFundAccountFlow(@DAOParam("shopFundAccountFlowData")ShopFundAccountFlowData shopFundAccountFlowData);

    @DAOAction(action = DAOActionType.LOAD)
    ShopFundAccountFlowData loadShopFundAccountFlow(@DAOParam("exchangeOrderId")int exchangeOrderId, @DAOParam("flowType")int flowType, @DAOParam("sourceType")int sourceType);

    @DAOAction(action = DAOActionType.UPDATE)
    int updateExchangeOrderId(@DAOParam("exchangeOrderId")int exchangeOrderId, @DAOParam("fundAccountFlowId")int fundAccountFlowId);

    @DAOAction(action = DAOActionType.LOAD)
    ShopFundAccountFlowData loadShopFundAccountFlowById(@DAOParam("flowId")int flowId);

}
