package com.dianping.ba.finance.exchange.api.enums;


/**
 * 业务类型
 */
public enum BusinessType {

    /**
     * 错误
     */
    DEFAULT(0),
    /**
     * 团购
     */
    GROUP_PURCHASE(1),
    /**
     * 预约预订
     */
    BOOKING(2),
    /**
     * 结婚亲子
     */
    WEDDING(3),
    /**
     * 储值卡
     */
    PREPAID_CARD(4),

    /**
     * 本地广告
     */
    ADVERTISEMENT(5),

    /**
     * 闪惠
     */
    SHAN_HUI(6),

    /**
     * 费用
     */
    EXPENSE(7),

    /**
     * 闪付
     */
    SHAN_FU(8);

    private int businessType;

    private BusinessType(int businessType) {
        this.setBusinessType(businessType);
    }

    @Override
    public String toString() {
        switch (businessType) {
            case 1:
                return "团购";
            case 2:
                return "预约预订";
            case 3:
                return "结婚亲子";
            case 4:
                return "储值卡" ;
            case 5:
                return "广告" ;
            case 6:
                return "闪惠";
            case 7:
                return "费用";
            case 8:
                return "闪付";
            default:
                return "默认";
        }
    }

    public static BusinessType valueOf(int value){
        switch (value){
            case 1:
                return GROUP_PURCHASE;
            case 2:
                return BOOKING;
            case 3:
                return WEDDING;
            case 4:
                return PREPAID_CARD;
            case 5:
                return ADVERTISEMENT;
            case 6:
                return SHAN_HUI;
            case 7:
                return EXPENSE;
            case 8:
                return SHAN_FU;
            default:
                return DEFAULT;
        }
    }

	public static BusinessType valueOfPayCentre(int value){
		switch (value){
			case 1:
				return GROUP_PURCHASE;
			case 2:
				return BOOKING;
			case 7:
				return WEDDING;
			case 8:
				return PREPAID_CARD;
			case 6:
				return ADVERTISEMENT;
            //收款的业务类型，暂时没有闪惠
            //收款的业务类型，暂时没有费用
            //收款的业务类型，暂时没有闪付
			default:
				return DEFAULT;
		}
	}

    public  String toString(int businessType) {
        this.setBusinessType(businessType);
        return this.toString();
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public int value(){
        return businessType;
    }
}
