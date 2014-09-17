package com.dianping.ba.finance.exchange.siteweb.constants;

import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.PayOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.ReceiveNotifyStatus;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderStatus;

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
            put(PayOrderStatus.REFUND.value(), "退票");
            put(PayOrderStatus.SUSPEND.value(), "暂停");
        }
    };

    public final static Map<Integer, String> RNSTATUS_OPTION = new HashMap<Integer, String>() {
        {
            put(ReceiveNotifyStatus.DEFAULT.value(), "全部");
            put(ReceiveNotifyStatus.INIT.value(), ReceiveNotifyStatus.INIT.toString());
            put(ReceiveNotifyStatus.MATCHED.value(), ReceiveNotifyStatus.MATCHED.toString());
            put(ReceiveNotifyStatus.REJECT.value(), ReceiveNotifyStatus.REJECT.toString());
            put(ReceiveNotifyStatus.CONFIRMED.value(), ReceiveNotifyStatus.CONFIRMED.toString());
        }
    };

	public final static Map<Integer, String> ROSTATUS_OPTION = new HashMap<Integer, String>() {
		{
			put(ReceiveOrderStatus.DEFAULT.value(), "全部");
			put(ReceiveOrderStatus.UNCONFIRMED.value(), ReceiveOrderStatus.UNCONFIRMED.toString());
			put(ReceiveOrderStatus.CONFIRMED.value(), ReceiveOrderStatus.CONFIRMED.toString());
		}
	};

    public final static Map<Integer, String> BUSINESSTYPE_OPTION = new HashMap<Integer, String>() {
        {
            put(BusinessType.DEFAULT.value(), "请选择业务类型");
            put(BusinessType.GROUP_PURCHASE.value(), "团购");
            put(BusinessType.SHAN_HUI.value(), "闪惠");
            put(BusinessType.BOOKING.value(), "预约预订");
            put(BusinessType.PREPAID_CARD.value(), "储值卡");
            put(BusinessType.EXPENSE.value(), "费用");
        }
    };

    public final static Map<Integer, String> PRODUCTLINE_OPTION = new HashMap<Integer, String>() {
        {
            put(BusinessType.DEFAULT.value(), "请选择产品线");
            put(BusinessType.GROUP_PURCHASE.value(), "团购");
            put(BusinessType.ADVERTISEMENT.value(), "广告");
        }
    };
}
