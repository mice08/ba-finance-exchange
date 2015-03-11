package com.dianping.ba.finance.exchange.api.enums;

/**
 * Created by will on 15-3-6.
 */
public enum AccountEntrySourceType {

    DEFAULT(0),
    /**
     * 付款单
     */
    PAY_ORDER(1),
    /**
     * 银行
     */
    BANK(2);

    private int sourceType;

    private AccountEntrySourceType(int sourceType) {
        this.setSourceType(sourceType);
    }

    @Override
    public String toString() {
        switch (sourceType) {
            case 1:
                return "付款单";
            case 2:
                return "银行";
            default:
                return "异常";
        }
    }

    public static AccountEntrySourceType valueOf(int value){
        switch (value){
            case 1:
                return PAY_ORDER;
            case 2:
                return BANK;
            default:
                return DEFAULT;
        }
    }

    public int value() {
        return this.sourceType;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }
}
