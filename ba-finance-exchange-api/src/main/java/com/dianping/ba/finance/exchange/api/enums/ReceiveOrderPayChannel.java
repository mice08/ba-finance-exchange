package com.dianping.ba.finance.exchange.api.enums;

/**
 * 收款单支付类型
 */
public enum ReceiveOrderPayChannel {

    /**
     * 默认
     */
    DEFAULT(0, "错误"),
    /**
     * 现金
     */
    CASH(1, "现金"),
    /**
     * 支票
     */
    CHECK(2, "支票"),
    /**
     * 电汇
     */
    TELEGRAPHIC_TRANSFER(3, "电汇"),
    /**
     * 贷记凭证
     */
    CREDIT_VOUCHER(4, "贷记凭证"),
    /**
     * POS机-快钱
     */
    POS_MACHINE(5, "POS机-快钱"),
    /**
     * 在线充值
     */
    ONLINE_RECHARGE(6, "在线充值");

    private int channel;

    private String description;


    private ReceiveOrderPayChannel(int channel, String description) {
        this.channel = channel;
        this.description = description;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
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

	public static ReceiveOrderPayChannel valueOfPayCentre(int payChannel, int payMethod) {
		if (payChannel == 10 && payMethod == 5) {
			return POS_MACHINE;
		} else {
			return DEFAULT;
		}
	}


	public int value(){
        return channel;
    }
}
