package com.dianping.ba.finance.exchange.siteweb.constants;

import com.dianping.ba.finance.exchange.api.enums.*;

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
            put(PayOrderStatus.EXPORT_PAYING.value(), "导出支付中");
            put(PayOrderStatus.PAY_SUCCESS.value(), "支付成功");
            put(PayOrderStatus.REFUND.value(), "退票");
            put(PayOrderStatus.SUSPEND.value(), "暂停");
            put(PayOrderStatus.BANK_PAYING.value(), "银行支付中");
            put(PayOrderStatus.PAY_FAILED.value(), "支付失败");
            put(PayOrderStatus.SUBMIT_FOR_PAY.value(), "提交支付中");
            put(PayOrderStatus.SYSTEM_ERROR.value(), "系统异常");
            put(PayOrderStatus.SUBMIT_FAILED.value(), "提交支付失败");

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
            put(BusinessType.ADVERTISEMENT.value(), "广告");
            put(BusinessType.SHAN_HUI.value(), "闪惠");
            put(BusinessType.BOOKING.value(), "预约预订");
            put(BusinessType.PREPAID_CARD.value(), "储值卡");
            put(BusinessType.EXPENSE.value(), "费用");
            put(BusinessType.SHAN_FU.value(), "闪付");
            put(BusinessType.MOVIE.value(), "电影");
        }
    };

    public final static Map<Integer, String> PRODUCTLINE_OPTION = new HashMap<Integer, String>() {
        {
            put(BusinessType.DEFAULT.value(), "请选择产品线");
            put(BusinessType.GROUP_PURCHASE.value(), "团购");
            put(BusinessType.ADVERTISEMENT.value(), "广告");
        }
    };

    public final static Map<Integer, String> PAYTYPE_OPTION = new HashMap<Integer, String>() {
        {
            put(PayType.GROUPON_SETTLE.value(), "团购结算款");
            put(PayType.GUARANTEE.value(), "保底款");
            put(PayType.OVER_GUARANTEE.value(), "超保底");
            put(PayType.GROUPON_BOND.value(), "团购保证金");
            put(PayType.EXPENSE_SETTLE.value(),"费用结算款");
            put(PayType.SHAN_HUI_SETTLE.value(), "闪惠结算款");
            put(PayType.SHAN_FU_SETTLE.value(),"闪付结算款");
            put(PayType.MOVIE_SETTLE.value(),"电影结算款");
        }
    };
}
