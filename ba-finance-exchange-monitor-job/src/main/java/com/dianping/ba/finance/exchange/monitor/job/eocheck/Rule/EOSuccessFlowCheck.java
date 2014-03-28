package com.dianping.ba.finance.exchange.monitor.job.eocheck.Rule;

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

    private ShopFundAccountFlowMonitorService shopFundAccountFlowMonitorService;

    @Override
    public boolean filter(ExchangeOrderMonitorData exchangeOrderMonitorData) {
        return exchangeOrderMonitorData.getStatus() == ExchangeOrderStatus.SUCCESS.value();
    }

    @Override
    public EOCheckResult check(ExchangeOrderMonitorData exchangeOrderMonitorData) {
        List<ShopFundAccountFlowMonitorData> flowDataList = shopFundAccountFlowMonitorService.findShopFundAccountFlowData(exchangeOrderMonitorData.getEoId());
        if (CollectionUtils.isEmpty(flowDataList)) {
            return createResult(false, checkIfTimeout(exchangeOrderMonitorData.getLastUpdateDate()), ExceptionType.EO_SUCCESS_WITHOUT_OUT_FLOW);
        } else if (flowDataList.size() > 2) {
            return createResult(false, checkIfTimeout(exchangeOrderMonitorData.getLastUpdateDate()), ExceptionType.EO_SUCCESS_WITH_MORE_FLOW);
        }
        return createValidResult();
    }

    public void setShopFundAccountFlowMonitorService(ShopFundAccountFlowMonitorService shopFundAccountFlowMonitorService) {
        this.shopFundAccountFlowMonitorService = shopFundAccountFlowMonitorService;
    }
}
