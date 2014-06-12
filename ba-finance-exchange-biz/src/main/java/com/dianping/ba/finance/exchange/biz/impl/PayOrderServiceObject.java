package com.dianping.ba.finance.exchange.biz.impl;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayOrderService;
import com.dianping.ba.finance.exchange.api.datas.PayOrderData;
import com.dianping.ba.finance.exchange.api.dtos.RefundDTO;
import com.dianping.ba.finance.exchange.api.dtos.RefundResultDTO;
import com.dianping.ba.finance.exchange.biz.dao.PayOrderDao;
import com.dianping.ba.finance.exchange.biz.utils.BizUtils;
import com.dianping.finance.common.aop.annotation.Log;
import com.dianping.finance.common.aop.annotation.ReturnDefault;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 处理付款单的Service类
 */
public class PayOrderServiceObject implements PayOrderService {

    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.biz.monitor.PayOrderServiceObject");

    private static final int DUPLICATE_RETRY_TIMES = 5;

    private PayOrderDao payOrderDao;

    @Log(logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public int createPayOrder(PayOrderData payOrderData) {
        int times = DUPLICATE_RETRY_TIMES;
        do {
            try {
                // 主键冲突，重新生成PayCode再插入
                payOrderData.setPayCode(BizUtils.generatePayCode());
                int poId = payOrderDao.insertPayOrder(payOrderData);
                payOrderData.setPoId(poId);
                return poId;
            } catch (Exception e) {
                MONITOR_LOGGER.error(String.format("severity=[1] PayOrderServiceObject.createPayOrder error! payOrderData=%s", payOrderData), e);
                times--;
            }
        } while(times > 0);
        return -1;
    }

    @Log(severity = 1, logBefore = true, logAfter = true)
    @ReturnDefault
    @Override
    public RefundResultDTO refundPayOrder(List<RefundDTO> refundDTOList, int loginId) {
        RefundResultDTO refundResultDTO = new RefundResultDTO();
        if (CollectionUtils.isEmpty(refundDTOList)) {
            return refundResultDTO;
        }
        // TODO

        return refundResultDTO;
    }

    public void setPayOrderDao(PayOrderDao payOrderDao) {
        this.payOrderDao = payOrderDao;
    }
}
