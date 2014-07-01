package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.ba.finance.exchange.api.ReceiveOrderService;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderResultBean;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderUpdateBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.ReceiveType;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveOrderDao;
import com.dianping.ba.finance.exchange.biz.producer.ReceiveOrderResultNotify;
import com.dianping.core.type.PageModel;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by noahshen on 14-6-17.
 */
public class ReceiveOrderServiceObject implements ReceiveOrderService {

    private ReceiveOrderDao receiveOrderDao;

    private ReceiveOrderResultNotify receiveOrderResultNotify;

    @Log(severity = 1, logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public int createReceiveOrder(ReceiveOrderData receiveOrderData) {
        receiveOrderData.setAddTime(new Date());
        receiveOrderData.setUpdateTime(new Date());
        if (ReceiveOrderStatus.CONFIRMED.value() == receiveOrderData.getStatus()) {
            receiveOrderData.setReceiveTime(new Date());
        }
        //手工录入财务自动生成
        if(StringUtils.isBlank(receiveOrderData.getTradeNo())){
            receiveOrderData.setTradeNo("FS-"+System.nanoTime());
        }
        int roId = receiveOrderDao.insertReceiveOrderData(receiveOrderData);
        receiveOrderData.setRoId(roId);

        if (ReceiveOrderStatus.CONFIRMED.value() == receiveOrderData.getStatus()) {
            ReceiveOrderResultBean receiveOrderResultBean = buildReceiveOrderResultBean(receiveOrderData, receiveOrderData.getAddLoginId());
            receiveOrderResultNotify.receiveResultNotify(receiveOrderResultBean);
        }
        return roId;
    }

    private ReceiveOrderResultBean buildReceiveOrderResultBean(ReceiveOrderData receiveOrderData, int loginId) {
        ReceiveOrderResultBean receiveOrderResultBean = new ReceiveOrderResultBean();
        receiveOrderResultBean.setBankId(receiveOrderData.getBankID());
        receiveOrderResultBean.setBankReceiveTime(receiveOrderData.getBankReceiveTime());
        receiveOrderResultBean.setBizContent(receiveOrderData.getBizContent());
        BusinessType businessType = BusinessType.valueOf(receiveOrderData.getBusinessType());
        receiveOrderResultBean.setBusinessType(businessType);
        receiveOrderResultBean.setCustomerId(receiveOrderData.getCustomerId());
        receiveOrderResultBean.setLoginId(loginId);
        receiveOrderResultBean.setMemo(receiveOrderData.getMemo());
        ReceiveOrderPayChannel payChannel = ReceiveOrderPayChannel.valueOf(receiveOrderData.getPayChannel());
        receiveOrderResultBean.setPayChannel(payChannel);
        receiveOrderResultBean.setPayTime(receiveOrderData.getPayTime());
        receiveOrderResultBean.setReceiveAmount(receiveOrderData.getReceiveAmount());
        receiveOrderResultBean.setReceiveTime(receiveOrderData.getReceiveTime());
        ReceiveType receiveType = ReceiveType.valueOf(receiveOrderData.getReceiveType());
        receiveOrderResultBean.setReceiveType(receiveType);
        receiveOrderResultBean.setRoId(receiveOrderData.getRoId());
        receiveOrderResultBean.setShopId(receiveOrderData.getShopId());
        receiveOrderResultBean.setTradeNo(receiveOrderData.getTradeNo());

        return receiveOrderResultBean;
    }

    @Log(severity = 1, logAfter = true)
    @ReturnDefault
    @Override
    public PageModel paginateReceiveOrderList(ReceiveOrderSearchBean receiveOrderSearchBean, int page, int max) {
        return receiveOrderDao.paginateReceiveOrderList(receiveOrderSearchBean, page, max);
    }

    @Log(severity = 1, logAfter = true)
    @ReturnDefault
    @Override
    public BigDecimal loadReceiveOrderTotalAmountByCondition(ReceiveOrderSearchBean receiveOrderSearchBean) {
        return receiveOrderDao.loadReceiveOrderTotalAmountByCondition(receiveOrderSearchBean);
    }

	@Log(severity = 1, logAfter = true)
	@ReturnDefault
	@Override
	public ReceiveOrderData loadReceiveOrderByTradeNo(String tradeNo) {
		return receiveOrderDao.loadReceiveOrderByTradeNo(tradeNo);
	}

	@Log(severity = 1, logAfter = true)
	@ReturnDefault
	@Override
	public boolean dropReceiveOrder(int roId, String memo) {
		ReceiveOrderUpdateBean updateBean = new ReceiveOrderUpdateBean();
		updateBean.setStatus(ReceiveOrderStatus.INVALID.value());
		updateBean.setMemo(memo);
		return receiveOrderDao.updateReceiveOrderByRoId(roId, updateBean) > 0;
	}

	@Log(severity = 1, logAfter = true)
	@ReturnDefault
	@Override
	public boolean updateReverseRoId(int originRoId, int reverseRoId) {
		ReceiveOrderUpdateBean updateBean = new ReceiveOrderUpdateBean();
		updateBean.setReverseRoId(reverseRoId);
		return receiveOrderDao.updateReceiveOrderByRoId(originRoId, updateBean) > 0;
	}

    @Log(severity = 1, logAfter = true)
    @ReturnDefault
    @Override
    public int updateReceiveOrder(ReceiveOrderData receiveOrderData){
        return receiveOrderDao.updateReceiveOrder(receiveOrderData);
    }

	public void setReceiveOrderDao(ReceiveOrderDao receiveOrderDao) {
        this.receiveOrderDao = receiveOrderDao;
    }

    public void setReceiveOrderResultNotify(ReceiveOrderResultNotify receiveOrderResultNotify) {
        this.receiveOrderResultNotify = receiveOrderResultNotify;
    }
}
