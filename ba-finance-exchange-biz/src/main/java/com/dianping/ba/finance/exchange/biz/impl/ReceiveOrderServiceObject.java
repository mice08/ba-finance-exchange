package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.RORNMatchFireService;
import com.dianping.ba.finance.exchange.api.RORNMatchService;
import com.dianping.ba.finance.exchange.api.ReceiveNotifyService;
import com.dianping.ba.finance.exchange.api.ReceiveOrderService;
import com.dianping.ba.finance.exchange.api.beans.ReceiveNotifyResultBean;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderResultBean;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderSearchBean;
import com.dianping.ba.finance.exchange.api.beans.ReceiveOrderUpdateBean;
import com.dianping.ba.finance.exchange.api.datas.ReceiveCalResultData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveNotifyData;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.enums.*;
import com.dianping.ba.finance.exchange.biz.dao.ReceiveOrderDao;
import com.dianping.ba.finance.exchange.biz.producer.ReceiveNotifyResultNotify;
import com.dianping.ba.finance.exchange.biz.producer.ReceiveOrderResultNotify;
import com.dianping.core.type.PageModel;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import com.dianping.finance.common.util.DateUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by noahshen on 14-6-17.
 */
public class ReceiveOrderServiceObject implements ReceiveOrderService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.monitor.ReceiveOrderServiceObject");

    private ReceiveOrderDao receiveOrderDao;

    private RORNMatchFireService rornMatchFireService;

    private RORNMatchService rornMatchService;

    private ReceiveOrderResultNotify receiveOrderResultNotify;

    private ReceiveNotifyService receiveNotifyService;

    private ReceiveNotifyResultNotify receiveNotifyResultNotify;

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
            receiveOrderData.setTradeNo("FS-" + System.nanoTime());
        }
        int rnId = -1;
        // 如果录入带有收款通知，进行匹配
        String applicationId = receiveOrderData.getApplicationId();
        if (StringUtils.isNotBlank(applicationId)) {
            ReceiveNotifyData rnData = receiveNotifyService.loadUnmatchedReceiveNotifyByApplicationId(ReceiveNotifyStatus.INIT, receiveOrderData.getBusinessType(), applicationId);
            if (rnData == null) {
                MONITOR_LOGGER.error(String.format("severity=[1] ReceiveOrderServiceObject.createReceiveOrder ReceiveNotifyData no found! biz=%s, applicationId=%s", receiveOrderData.getBusinessType(), applicationId));
                return -1;
            }
            // 手工录入没有付款方名字
            if (StringUtils.isBlank(receiveOrderData.getPayerAccountName())) {
                receiveOrderData.setPayerAccountName(rnData.getPayerName());
            }
            // 手工录入POS，没有银行到账时间，设置为打款时间
            if (receiveOrderData.getBankReceiveTime() == null) {
                receiveOrderData.setBankReceiveTime(rnData.getPayTime());
            }
            // 手工录入与填写的收款通知不匹配，添加失败
            if (!rornMatchService.doMatch(receiveOrderData, rnData)) {
                MONITOR_LOGGER.error(String.format("severity=[1] ReceiveOrderServiceObject.createReceiveOrder ReceiveNotifyData and ReceiveOrderData no match! roId=%s, rnId=%s", receiveOrderData.getRoId(), rnData.getReceiveNotifyId()));
                return -1;
            }
            rnId = rnData.getReceiveNotifyId();
        }

        int roId = receiveOrderDao.insertReceiveOrderData(receiveOrderData);
        receiveOrderData.setRoId(roId);

        if (rnId > 0) {
            boolean update = receiveNotifyService.updateReceiveNotifyConfirm(roId, rnId);
            if (!update) {
                MONITOR_LOGGER.error(String.format("severity=[1] ReceiveOrderServiceObject.createReceiveOrder updateReceiveNotifyConfirm error! roId=%s, rnId=%s", roId, rnId));
                return -1;
            }
        }
        if (ReceiveOrderStatus.CONFIRMED.value() == receiveOrderData.getStatus()) {
            ReceiveOrderResultBean receiveOrderResultBean = buildReceiveOrderResultBean(receiveOrderData, receiveOrderData.getAddLoginId());
            receiveOrderResultNotify.receiveResultNotify(receiveOrderResultBean);
            rornMatchFireService.executeMatchingForReceiveOrderConfirmed(receiveOrderData);
            if (StringUtils.isNotBlank(applicationId)) {
                ReceiveNotifyData receiveNotifyData = receiveNotifyService.loadReceiveNotifyByApplicationId(applicationId);
                if (receiveNotifyData != null) {
                    ReceiveNotifyResultBean receiveNotifyResultBean = buildReceiveNotifyResultBean(receiveNotifyData, ReceiveNotifyResultStatus.CONFIRMED);
                    receiveNotifyResultNotify.resultNotify(receiveNotifyResultBean);
                }
            }
        } else {
            rornMatchFireService.executeMatchingForNewReceiveOrder(receiveOrderData);
        }
        return roId;
    }

    private ReceiveNotifyResultBean buildReceiveNotifyResultBean(ReceiveNotifyData receiveNotifyData, ReceiveNotifyResultStatus status) {
        ReceiveNotifyResultBean receiveNotifyResultBean = new ReceiveNotifyResultBean();
        receiveNotifyResultBean.setApplicationId(receiveNotifyData.getApplicationId());
        receiveNotifyResultBean.setBusinessType(receiveNotifyData.getBusinessType());
        receiveNotifyResultBean.setMemo(status.toString());
        receiveNotifyResultBean.setReceiveNotifyId(receiveNotifyData.getReceiveNotifyId());
        receiveNotifyResultBean.setStatus(status);
        return receiveNotifyResultBean;
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

        receiveOrderResultBean.setApplicationId(receiveOrderData.getApplicationId());

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
    public int updateReceiveOrderConfirm(ReceiveOrderUpdateBean receiveOrderUpdateBean) {
        if (!allowReceiveOrderUpdateBeanConfirmStatus(receiveOrderUpdateBean)) {
            return -1;
        }
        ReceiveOrderData receiveOrderUpdateData = buildReceiveOrderUpdateData(receiveOrderUpdateBean);
        int result = receiveOrderDao.updateReceiveOrder(receiveOrderUpdateData);
        if (result > 0 && ReceiveOrderStatus.CONFIRMED.value() == receiveOrderUpdateData.getStatus()) {
            ReceiveOrderData receiveOrderData = loadReceiveOrderDataByRoId(receiveOrderUpdateData.getRoId());
            ReceiveOrderResultBean receiveOrderResultBean = buildReceiveOrderResultBean(receiveOrderData, receiveOrderData.getUpdateLoginId());
            receiveOrderResultNotify.receiveResultNotify(receiveOrderResultBean);
            String applicationId = receiveOrderData.getApplicationId();
            if (StringUtils.isNotBlank(applicationId)) {
                rornMatchFireService.executeMatchingForReceiveOrderConfirmed(receiveOrderData);
                ReceiveNotifyData receiveNotifyData = receiveNotifyService.loadReceiveNotifyByApplicationId(applicationId);
                if (receiveNotifyData != null) {
                    ReceiveNotifyResultBean receiveNotifyResultBean = buildReceiveNotifyResultBean(receiveNotifyData, ReceiveNotifyResultStatus.CONFIRMED);
                    receiveNotifyResultNotify.resultNotify(receiveNotifyResultBean);
                }
            }
        }
        return result;
    }

	/**
	 * 在原有业务逻辑的基础上有一些改动，针对团购的收款单的确认，去掉对合同号的限制
	 * by 成亚雄
	 * @param receiveOrderUpdateBean
	 * @return
	 */
    private boolean allowReceiveOrderUpdateBeanConfirmStatus(ReceiveOrderUpdateBean receiveOrderUpdateBean){
        ReceiveOrderData temp = loadReceiveOrderDataByRoId(receiveOrderUpdateBean.getRoId());
        //已确认状态判断字段 推广——客户名，系统入账时间，收款类型，业务类型
        if (receiveOrderUpdateBean.getStatus() != ReceiveOrderStatus.CONFIRMED.value()) {
            return true;
        }
        if (temp.getBusinessType() == BusinessType.GROUP_PURCHASE.value()) {
            return receiveOrderUpdateBean.getReceiveTime() != null
                    && receiveOrderUpdateBean.getCustomerId() > 0
                    && receiveOrderUpdateBean.getReceiveType().value() > 0;
        } else {
            return receiveOrderUpdateBean.getReceiveTime() != null
                    && receiveOrderUpdateBean.getCustomerId() > 0
                    && StringUtils.isNotEmpty(receiveOrderUpdateBean.getBizContent())
                    && receiveOrderUpdateBean.getReceiveType().value() > 0;
        }
    }

    private ReceiveOrderData buildReceiveOrderUpdateData(ReceiveOrderUpdateBean receiveOrderUpdateBean) {
        ReceiveOrderData receiveOrderData = new ReceiveOrderData();
        receiveOrderData.setRoId(receiveOrderUpdateBean.getRoId());
        receiveOrderData.setStatus(receiveOrderUpdateBean.getStatus());
        receiveOrderData.setCustomerId(receiveOrderUpdateBean.getCustomerId());
        receiveOrderData.setShopId(receiveOrderUpdateBean.getShopId());
        receiveOrderData.setBizContent(receiveOrderUpdateBean.getBizContent());
        receiveOrderData.setReceiveTime(receiveOrderUpdateBean.getReceiveTime());
        receiveOrderData.setMemo(receiveOrderUpdateBean.getMemo());
        receiveOrderData.setReverseRoId(receiveOrderUpdateBean.getReverseRoId());
        receiveOrderData.setApplicationId(receiveOrderUpdateBean.getApplicationId());
        receiveOrderData.setReceiveType(receiveOrderUpdateBean.getReceiveType() == null ? 0 : receiveOrderUpdateBean.getReceiveType().value());
        receiveOrderData.setUpdateLoginId(receiveOrderUpdateBean.getUpdateLoginId());
        return receiveOrderData;
    }

    @Log(severity = 1, logAfter = true)
    @ReturnDefault
    @Override
    public ReceiveOrderData loadReceiveOrderDataByRoId(int roId){
        return receiveOrderDao.loadReceiveOrderDataByRoId(roId);
    }

    @Log(severity = 3, logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public List<ReceiveOrderData> findUnmatchAndUnconfirmedReceiveOrder(ReceiveOrderStatus status) {
        return receiveOrderDao.findUnmatchAndUnconfirmedReceiveOrder(status.value());
    }

    @Log(severity = 1, logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public boolean confirmReceiveOrderAndReceiveNotify(int roId, int rnId, int loginId) {
        ReceiveNotifyData rnData = receiveNotifyService.loadMatchedReceiveNotify(rnId, roId);
        if (rnData == null) {
            MONITOR_LOGGER.error(String.format("severity=[1] ReceiveOrderServiceObject.confirmReceiveOrderAndReceiveNotify No matched ReceiveNotifyData found! roId=%s, rnId=%s", roId, rnId));
            return false;
        }
        ReceiveOrderData roData = receiveOrderDao.loadReceiveOrderDataByRoId(roId);
        if (roData == null) {
            MONITOR_LOGGER.error(String.format("severity=[1] ReceiveOrderServiceObject.confirmReceiveOrderAndReceiveNotify No matched ReceiveOrderData found! roId=%s, rnId=%s", roId, rnId));
            return false;
        }

        boolean update = receiveNotifyService.updateReceiveNotifyConfirm(roId, rnId);
        if (!update) {
            MONITOR_LOGGER.error(String.format("severity=[1] ReceiveOrderServiceObject.confirmReceiveOrderAndReceiveNotify updateReceiveNotifyConfirm error! roId=%s, rnId=%s", roId, rnId));
            return false;
        }

        ReceiveOrderUpdateBean receiveOrderUpdateBean = buildReceiveOrderUpdateBean(roData, rnData, loginId);
        int u = updateReceiveOrderConfirm(receiveOrderUpdateBean);
        return u == 1;
    }

    @Override
    public boolean manuallyUpdateReceiveOrder(ReceiveOrderUpdateBean receiveOrderUpdateBean) {
        String applicationId = receiveOrderUpdateBean.getApplicationId();
        if (receiveOrderUpdateBean.getStatus() != ReceiveOrderStatus.CONFIRMED.value()
                || StringUtils.isEmpty(applicationId)) {
            int u = updateReceiveOrderConfirm(receiveOrderUpdateBean);
            return u == 1;
        }

        // 带有applicationId，及确认的收款单
        if (relateRORN(receiveOrderUpdateBean.getRoId(), receiveOrderUpdateBean.getApplicationId())) {
            int u = updateReceiveOrderConfirm(receiveOrderUpdateBean);
            return u == 1;
        }
        return false;
    }

    private boolean relateRORN(int roId, String applicationId){
        ReceiveOrderData roData = receiveOrderDao.loadReceiveOrderDataByRoId(roId);
        if (roData == null) {
            MONITOR_LOGGER.error(String.format("severity=[1] ReceiveOrderServiceObject.manuallyUpdateReceiveOrder ReceiveOrderData no found! roId=%s", roId));
            return false;
        }
        ReceiveNotifyData rnData = receiveNotifyService.loadUnmatchedReceiveNotifyByApplicationId(ReceiveNotifyStatus.INIT, roData.getBusinessType(), applicationId);
        if (rnData == null) {
            MONITOR_LOGGER.error(String.format("severity=[1] ReceiveOrderServiceObject.manuallyUpdateReceiveOrder ReceiveNotifyData no found! biz=%s, applicationId=%s", roData.getBusinessType(), roData.getApplicationId()));
            return false;
        }
        if (!rornMatchService.doMatch(roData, rnData)) {
            MONITOR_LOGGER.error(String.format("severity=[1] ReceiveOrderServiceObject.manuallyUpdateReceiveOrder ReceiveNotifyData and ReceiveOrderData no match! roId=%s, rnId=%s", roData.getRoId(), rnData.getReceiveNotifyId()));
            return false;
        }
        boolean update = receiveNotifyService.updateReceiveNotifyConfirm(roData.getRoId(), rnData.getReceiveNotifyId());
        if (!update) {
            MONITOR_LOGGER.error(String.format("severity=[1] ReceiveOrderServiceObject.manuallyUpdateReceiveOrder updateReceiveNotifyConfirm error! roId=%s, rnId=%s", roData.getRoId(), rnData.getReceiveNotifyId()));
            return false;
        }
        return true;
    }

    private ReceiveOrderUpdateBean buildReceiveOrderUpdateBean(ReceiveOrderData roData, ReceiveNotifyData rnData, int loginId) {
        ReceiveOrderUpdateBean updateBean = new ReceiveOrderUpdateBean();
        updateBean.setApplicationId(rnData.getApplicationId());
        updateBean.setBizContent(StringUtils.isNotBlank(roData.getBizContent()) ? roData.getBizContent() : rnData.getBizContent());
        updateBean.setCustomerId(roData.getCustomerId() > 0 ? roData.getCustomerId() : rnData.getCustomerId());
        updateBean.setMemo(roData.getMemo());
        updateBean.setReceiveTime(new Date());
        updateBean.setReceiveType(ReceiveType.valueOf(roData.getReceiveType()));
        updateBean.setRoId(roData.getRoId());
        updateBean.setShopId(roData.getShopId());
        updateBean.setStatus(ReceiveOrderStatus.CONFIRMED.value());
        updateBean.setUpdateLoginId(loginId);
        return updateBean;
    }


    @Override
    public List<ReceiveCalResultData> findCalculatedReceiveResult(ReceiveOrderSearchBean receiveOrderSearchBean) {
        Date addTimeBegin = receiveOrderSearchBean.getAddTimeBegin();
        if (addTimeBegin == null) {
            addTimeBegin = new Date();
        }
        try {
            String voucherDate = DateUtils.format("yyyy-MM-01", addTimeBegin);
            return receiveOrderDao.findCalculatedReceiveResult(receiveOrderSearchBean, voucherDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setReceiveOrderDao(ReceiveOrderDao receiveOrderDao) {
        this.receiveOrderDao = receiveOrderDao;
    }

    public void setReceiveOrderResultNotify(ReceiveOrderResultNotify receiveOrderResultNotify) {
        this.receiveOrderResultNotify = receiveOrderResultNotify;
    }

    public void setRornMatchFireService(RORNMatchFireService rornMatchFireService) {
        this.rornMatchFireService = rornMatchFireService;
    }

    public void setReceiveNotifyService(ReceiveNotifyService receiveNotifyService) {
        this.receiveNotifyService = receiveNotifyService;
    }

    public void setRornMatchService(RORNMatchService rornMatchService) {
        this.rornMatchService = rornMatchService;
    }

    public void setReceiveNotifyResultNotify(ReceiveNotifyResultNotify receiveNotifyResultNotify) {
        this.receiveNotifyResultNotify = receiveNotifyResultNotify;
    }
}
