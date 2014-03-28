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
 * Time: 下午4:21
 * To change this template use File | Settings | File Templates.
 */
public class EOPendIngAndInitCheck extends EOCheckBase {

    private ShopFundAccountFlowMonitorService shopFundAccountFlowMonitorService;

    @Override
    public boolean filter(ExchangeOrderMonitorData exchangeOrderMonitorData) {
        return (exchangeOrderMonitorData.getStatus() == ExchangeOrderStatus.PENDING.value()
                || exchangeOrderMonitorData.getStatus() == ExchangeOrderStatus.INIT.value());
    }

    @Override
    public EOCheckResult check(ExchangeOrderMonitorData exchangeOrderMonitorData) {
        List<ShopFundAccountFlowMonitorData> flowDataList = shopFundAccountFlowMonitorService.findShopFundAccountFlowData(exchangeOrderMonitorData.getEoId());
        if (CollectionUtils.isEmpty(flowDataList)) {
            return createResult(false, true, ExceptionType.EO_INIT_PENDING_WITHOUT_FLOW);
        } else if (flowDataList.size() > 1) {
            return createResult(false, true, ExceptionType.EO_INIT_PENDING_WITH_MORE_FLOW);
        }
        return createValidResult();
    }

    public void setShopFundAccountFlowMonitorService(ShopFundAccountFlowMonitorService shopFundAccountFlowMonitorService) {
        this.shopFundAccountFlowMonitorService = shopFundAccountFlowMonitorService;
    }
}
