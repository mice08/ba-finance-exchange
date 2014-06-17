package com.dianping.ba.finance.exchange.api.enums;

/**
 * 收款单支付类型
 */
public enum ReceiveOrderPayChannel {

    /**
     * 默认
     */
    DEFAULT(0),
    /**
     * 现金
     */
    CASH(1),
    /**
     * 支票
     */
    CHECK(2),
    /**
     * 电汇
     */
    TELEGRAPHIC_TRANSFER(3),
    /**
     * 贷记凭证
     */
    CREDIT_VOUCHER(4),
    /**
     * POS机
     */
    POS_MACHINE(5),
    /**
     * 在线充值
     */
    ONLINE_RECHARGE(6);

    private int receiveOrderPayChannel;

    private ReceiveOrderPayChannel(int receiveOrderPayChannel) {
        this.setReceiveOrderPayChannel(receiveOrderPayChannel);
    }

    @Override
    public String toString() {
        switch (receiveOrderPayChannel) {
            case 1:
                return "现金";
            case 2:
                return "支票";
            case 3:
                return "电汇";
            case 4:
                return "贷记凭证";
            case 5:
                return "POS机";
            case 6:
                return "在线充值";
            default:
                return "错误";
        }
    }

    public static ReceiveOrderPayChannel valueOf(int value){
        switch (value){
            case 1:
                return CASH;
            case 2:
                return CHECK;
            case 3:
                return TELEGRAPHIC_TRANSFER;
            case 4:
                return CREDIT_VOUCHER;
            case 5:
                return POS_MACHINE;
            case 6:
                return ONLINE_RECHARGE;
            default:
                return DEFAULT;
        }
    }

    public int value(){
        return receiveOrderPayChannel;
    }

    public int getReceiveOrderPayChannel() {
        return receiveOrderPayChannel;
    }

    public void setReceiveOrderPayChannel(int receiveOrderPayChannel) {
        this.receiveOrderPayChannel = receiveOrderPayChannel;
    }
}
