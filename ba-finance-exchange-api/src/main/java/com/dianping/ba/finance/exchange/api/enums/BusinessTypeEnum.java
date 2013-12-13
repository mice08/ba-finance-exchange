package com.dianping.ba.finance.exchange.api.enums;

/**
 * 业务类型
 */
public enum BusinessTypeEnum {
    /**
     * 团购
     */
    GROUPPURCHASE(1),
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
    PREPAIDCARD(4);

    private int businessType;

    private BusinessTypeEnum(int businessType) {
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
            default:
                return "默认";
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
}

