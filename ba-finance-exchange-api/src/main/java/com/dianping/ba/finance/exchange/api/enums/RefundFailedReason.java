package com.dianping.ba.finance.exchange.api.enums;

/**
 * Created with IntelliJ IDEA.
 * User: huajiao.zou
 * Date: 14-2-19
 * Time: 下午4:05
 * To change this template use File | Settings | File Templates.
 */
public enum RefundFailedReason {
    /**
     * 0-默认错误
     */
    DEFAULT(0),
    /**
     * 1-数据库不存在该条信息
     */
    INFO_EMPTY(1),
    /**
     * 2-状态不正确
     */
    STATUS_ERROR(2);

    private int refundFailedReason;

    private RefundFailedReason(int refundFailedReason) {
        this.refundFailedReason = refundFailedReason;
    }

    public void setRefundFailedReason(int refundFailedReason) {
        this.refundFailedReason = refundFailedReason;
    }

    @Override
    public String toString(){
        switch (refundFailedReason){
            case 1:
                return "数据库不存在该条信息";
            case 2:
                return "前置状态错误";
            default:
                return "错误";
        }
    }

    public static RefundFailedReason valueOf(int value){
        switch (value){
            case 1:
                return INFO_EMPTY;
            case 2:
                return STATUS_ERROR;
            default:
                return DEFAULT;
        }
    }

    public int value(){
        return refundFailedReason;
    }
}
