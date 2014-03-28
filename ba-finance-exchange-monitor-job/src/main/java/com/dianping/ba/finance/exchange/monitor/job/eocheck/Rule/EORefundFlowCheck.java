package com.dianping.ba.finance.exchange.monitor.job.eocheck.Rule;

import com.dianping.ba.finance.exchange.api.enums.FlowType;
import com.dianping.ba.finance.exchange.api.enums.SourceType;
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
        int eoId = exchangeOrderMonitorData.getEoId();
        List<ShopFundAccountFlowMonitorData> shopFundAccountFlowMonitorDataList = shopFundAccountFlowMonitorService.findShopFundAccountFlowData(eoId);
        if(CollectionUtils.isEmpty(shopFundAccountFlowMonitorDataList)){
            return createResult(false, checkIfTimeout(exchangeOrderMonitorData.getLastUpdateDate()), ExceptionType.EO_REFUND_WITHOUT_FLOW);
        }else if(shopFundAccountFlowMonitorDataList.size() != 4){
            return createResult(false, checkIfTimeout(exchangeOrderMonitorData.getLastUpdateDate()), ExceptionType.EO_REFUND_WITH_WRONG_FLOW_COUNT);
        }
//        boolean hasPPInFlow = false;
//        boolean hasPPOutFlow = false;
//        boolean hasEOInFlow = false;
//        boolean hasEOOutFlow = false;
//        for(ShopFundAccountFlowMonitorData flowMonitorData: shopFundAccountFlowMonitorDataList){
//            int flowType = flowMonitorData.getFlowType();
//            int sourceType = flowMonitorData.getSourceType();
//            if(flowType == FlowType.IN.value() && sourceType == SourceType.PaymentPlan.value()){
//                hasEOInFlow = true;
//            }else if(flowType == FlowType.IN.value() && sourceType == SourceType.ExchangeOrder.value()){
//                hasEOInFlow = true;
//            }else if(flowType == FlowType.OUT.value() && sourceType == SourceType.PaymentPlan.value()){
//                hasPPOutFlow = true;
//            }else if(flowType == FlowType.OUT.value() && sourceType == SourceType.ExchangeOrder.value()){
//                hasEOOutFlow = true;
//            }
//        }
//        if(!hasPPInFlow){
//            return createResult(false, checkIfTimeout(exchangeOrderMonitorData.getLastUpdateDate()), ExceptionType.EO_REFUND_WITHOUT_PP_IN_FLOW);
//        }

        return createValidResult();
    }

    public void setShopFundAccountFlowMonitorService(ShopFundAccountFlowMonitorService shopFundAccountFlowMonitorService) {
        this.shopFundAccountFlowMonitorService = shopFundAccountFlowMonitorService;
    }

}
