package com.dianping.ba.finance.exchange.api.enums;

/**
 * 交易类型
 *
 * @author junjie.mao
 */
public enum SourceTypeEnum {

    /**
     * 默认
     */
    Default(0),
    /**
     * 付款计划
     */
    PaymentPlan(1),
    /**
     * 收款计划
     */
    ReceivablePlan(2),
    /**
     * 交易指令
     */
    ExchangeOrder(3);
    private int sourceType;

    private SourceTypeEnum(int sourceType) {
        this.setSourceType(sourceType);
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    @Override
    public String toString() {
        switch (sourceType) {
            case 1:
                return "付款计划";
            case 2:
                return "收款计划";
            case 3:
                return "交易指令";
            default:
                return "默认";
        }
    }

    public String clientNo() {
        switch (sourceType) {
            case 1:
                return "PP";
            case 2:
                return "RP";
            case 3:
                return "EX";
            default:
                return "ERROR";
        }
    }

    public String toString(int sourceType) {
        this.setSourceType(sourceType);
        return this.toString();
    }

    public int value() {
        return sourceType;
    }
}
