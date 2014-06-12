package com.dianping.ba.finance.exchange.siteweb.constants;

import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eric on 2014/6/11.
 */
public class OptionConstant {

    public final static Map<Integer, String> POSTATUS_OPTION = new HashMap<Integer, String>() {
        {
            put(PayOrderStatus.DEFAULT.value(), "全部");
            put(PayOrderStatus.INIT.value(), "初始");
            put(PayOrderStatus.EXPORT_PAYING.value(), "支付中");
            put(PayOrderStatus.PAY_SUCCESS.value(), "支付成功");
            put(PayOrderStatus.REFUND.value(),"退票");
        }
    };

    public final static Map<Integer, String> BUSINESSTYPE_OPTION = new HashMap<Integer, String>() {
        {
            put(BusinessType.DEFAULT.value(), "请选择业务类型");
            put(BusinessType.GROUP_PURCHASE.value(), "团购");
        }
    };
}
