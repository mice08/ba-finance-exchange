package com.dianping.ba.finance.exchange.api.enums;

/**
 * Created by Administrator on 2014/7/24.
 */
public enum ReceiveNotifyResultStatus {
    /**
    *异常
     */
    DEFAULT(0),
    /**
     * 处理成功
     */
    SUCCESS(1),
    /**
     * 处理失败
     */
    FAIL(2),
    /**
     * 驳回
     */
    REJECT(3);

    private int receiveNotifyResultStatus;

    private ReceiveNotifyResultStatus(int receiveNotifyResultStatus) {
        this.setReceiveNotifyResultStatus(receiveNotifyResultStatus);
    }

    public static ReceiveNotifyResultStatus valueOf(int value) {
        switch (value) {
            case 1:
                return SUCCESS;
            case 2:
                return FAIL;
            case 3:
                return REJECT;
            default:
                return DEFAULT;
        }
    }

    @Override
    public String toString() {
        switch (receiveNotifyResultStatus) {
            case 1:
                return "处理成功";
            case 2:
                return "处理失败";
            case 3:
                return "驳回";
            default:
                return "错误";
        }
    }

    public void setReceiveNotifyResultStatus(int receiveNotifyResultStatus) {
        this.receiveNotifyResultStatus = receiveNotifyResultStatus;
    }

    public int getReceiveNotifyResultStatus() {
        return receiveNotifyResultStatus;
    }

    public int value() {
        return receiveNotifyResultStatus;
    }
}
