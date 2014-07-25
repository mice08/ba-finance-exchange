package com.dianping.ba.finance.exchange.api.enums;

/**
 * Created by Administrator on 2014/7/24.
 */
public enum  ReceiveNotifyStatus {
    /**
     *异常
     */
    DEFAULT(0),
    /**
     *合法的收款申请通知
     */
    VALID_RECEIVENOTIFY(1),
    /**
     *超时
     */
    TIMEOUT(2),
    /**
     *非法的token
     */
    INVALID_TOKEN(3),
    /**
     *非法的金额
     */
    INVALID_RECEIVEAMOUNT(4),
    /**
     *重复的applicationId
     */
    DUPLICATE_APPLICATIONID(5)
    ;
    private int receiveNotifyStatus;

    private ReceiveNotifyStatus(int receiveNotifyStatus) {
        this.receiveNotifyStatus = receiveNotifyStatus;
    }

    public static ReceiveNotifyStatus valueOf(int value) {
        switch (value) {
            case 1:
                return VALID_RECEIVENOTIFY;
            case 2:
                return TIMEOUT;
            case 3:
                return INVALID_TOKEN;
            case 4:
                return INVALID_RECEIVEAMOUNT;
            case 5:
                return DUPLICATE_APPLICATIONID;
            default:
                return DEFAULT;
        }
    }

    public String toString(){
        switch (receiveNotifyStatus){
            case 1:
                return "合法的收款申请通知";
            case 2:
                return "超时";
            case 3:
                return "非法的token";
            case 4:
                return "非法的金额";
            case 5:
                return "重复的applicationId";
            default:
                return "错误";

        }
    }

    public int getReceiveNotifyStatus() {
        return receiveNotifyStatus;
    }

    public void setReceiveNotifyStatus(int receiveNotifyStatus) {
        this.receiveNotifyStatus = receiveNotifyStatus;
    }

    public int value(){
        return receiveNotifyStatus;
    }
}
