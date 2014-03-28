package com.dianping.ba.finance.exchange.monitor.job.eocheck.Rule;

import com.dianping.ba.finance.exchange.monitor.api.ShopFundAccountFlowMonitorService;
import com.dianping.ba.finance.exchange.monitor.api.datas.ExchangeOrderMonitorData;
import com.dianping.ba.finance.exchange.monitor.api.datas.ShopFundAccountFlowMonitorData;
import com.dianping.ba.finance.exchange.monitor.api.enums.ExceptionType;
import com.dianping.ba.finance.exchange.monitor.api.enums.ExchangeOrderStatus;
import com.dianping.ba.finance.exchange.monitor.job.eocheck.EOCheckBase;
import com.dianping.ba.finance.exchange.monitor.job.eocheck.EOCheckResult;
import com.dianping.ba.finance.exchange.monitor.job.utils.ConstantUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 14-3-28
 * Time: 上午9:59
 * To change this template use File | Settings | File Templates.
 */
public class EORefundFlowCheck extends EOCheckBase {

    private ShopFundAccountFlowMonitorService shopFundAccountFlowMonitorService;

    @Override
    public boolean filter(ExchangeOrderMonitorData exchangeOrderMonitorData) {
        return exchangeOrderMonitorData.getStatus() == ExchangeOrderStatus.FAIL.value();
    }

    @Override
    public EOCheckResult check(ExchangeOrderMonitorData exchangeOrderMonitorData) {
        setTimeout(ConstantUtils.refundTimeout);
        int eoId = exchangeOrderMonitorData.getEoId();
        List<ShopFundAccountFlowMonitorData> shopFundAccountFlowMonitorDataList = shopFundAccountFlowMonitorService.findShopFundAccountFlowData(eoId);
        if(CollectionUtils.isEmpty(shopFundAccountFlowMonitorDataList)){
            return createResult(false, checkIfTimeout(exchangeOrderMonitorData.getLastUpdateDate()), ExceptionType.EO_REFUND_WITHOUT_FLOW);
        }else if(shopFundAccountFlowMonitorDataList.size() != 4){
            return createResult(false, checkIfTimeout(exchangeOrderMonitorData.getLastUpdateDate()), ExceptionType.EO_REFUND_WITH_WRONG_FLOW_COUNT);
        }
        return createValidResult();
    }

    public void setShopFundAccountFlowMonitorService(ShopFundAccountFlowMonitorService shopFundAccountFlowMonitorService) {
        this.shopFundAccountFlowMonitorService = shopFundAccountFlowMonitorService;
    }

}
