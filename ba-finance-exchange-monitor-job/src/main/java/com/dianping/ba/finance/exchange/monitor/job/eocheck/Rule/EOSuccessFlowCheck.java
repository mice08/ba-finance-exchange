package com.dianping.ba.finance.exchange.monitor.job.eocheck.Rule;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.monitor.api.ShopFundAccountFlowMonitorService;
import com.dianping.ba.finance.exchange.monitor.api.datas.ExchangeOrderMonitorData;
import com.dianping.ba.finance.exchange.monitor.api.datas.ShopFundAccountFlowMonitorData;
import com.dianping.ba.finance.exchange.monitor.api.enums.ExceptionType;
import com.dianping.ba.finance.exchange.monitor.api.enums.ExchangeOrderStatus;
import com.dianping.ba.finance.exchange.monitor.job.eocheck.EOCheckBase;
import com.dianping.ba.finance.exchange.monitor.job.eocheck.EOCheckResult;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jingzhou.yan
 * Date: 14-3-27
 * Time: 下午6:49
 * To change this template use File | Settings | File Templates.
 */
public class EOSuccessFlowCheck extends EOCheckBase {

    private static final AvatarLogger monitorLogger = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.monitor.job.eocheck.Rule.EOSuccessFlowCheck");
    private ShopFundAccountFlowMonitorService shopFundAccountFlowMonitorService;

    @Override
    public boolean filter(ExchangeOrderMonitorData exchangeOrderMonitorData) {
        return exchangeOrderMonitorData.getStatus() == ExchangeOrderStatus.SUCCESS.value();
    }

    @Override
    public EOCheckResult check(ExchangeOrderMonitorData exchangeOrderMonitorData) {
        // EO状态为支付成功，需要在规定的时间间隔（如5min）存在一条对应的负向Flow。
        List<ShopFundAccountFlowMonitorData> flowDataList = shopFundAccountFlowMonitorService.findShopFundAccountFlowData(exchangeOrderMonitorData.getEoId());
        if (CollectionUtils.isEmpty(flowDataList)) {
            monitorLogger.error(String.format("ExchangeOrderMonitorData=%s, exceptionType=%s", exchangeOrderMonitorData, ExceptionType.EO_INITANDPENDING_WITH_NO_FLOW));
            return createResult(false, checkIfTimeout(exchangeOrderMonitorData.getLastUpdateDate()), ExceptionType.EO_SUCCESS_WITH_NO_MINUSFLOW);
        } else {
            if (flowDataList.size() > 1) {
                monitorLogger.error(String.format("ExchangeOrderMonitorData=%s, exceptionType=%s", exchangeOrderMonitorData, ExceptionType.EO_INITANDPENDING_WITH_MORE_FLOW));
                return createResult(false, checkIfTimeout(exchangeOrderMonitorData.getLastUpdateDate()), ExceptionType.EO_SUCCESS_WITH_MORE_MINUSFLOW);
            }
        }
        return createValidResult();
    }

    public void setShopFundAccountFlowMonitorService(ShopFundAccountFlowMonitorService shopFundAccountFlowMonitorService) {
        this.shopFundAccountFlowMonitorService = shopFundAccountFlowMonitorService;
    }
}
