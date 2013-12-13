package com.dianping.ba.finance.exchange.biz.dao;

import com.dianping.avatar.dao.GenericDao;
import com.dianping.avatar.dao.annotation.DAOAction;
import com.dianping.avatar.dao.annotation.DAOActionType;
import com.dianping.avatar.dao.annotation.DAOParam;
import com.dianping.ba.finance.exchange.api.beans.ShopFundAccountBean;
import com.dianping.ba.finance.exchange.api.datas.ShopFundAccountData;

/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 13-12-13
 * Time: 上午11:39
 * To change this template use File | Settings | File Templates.
 */
public interface ShopFundAccountDAO extends GenericDao{

    /**
     * 根据查询条件查找资金账户对象
     * @param shopFundAccountBean
     * @return
     */
    @DAOAction(action = DAOActionType.LOAD)
    ShopFundAccountData loadShopFundAccountData(@DAOParam("shopFundAccountBean") ShopFundAccountBean shopFundAccountBean);
}
