package com.dianping.ba.finance.exchange.receivemonitor.api.enums;


public enum ReceiveConfirmStatus {

	/**
	 *
	 */
    DEFAULT(0),

	CONFIRM_SUCCESS(1),

    CONFIRM_FAILURE(2);

    private int status;

    private ReceiveConfirmStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int value(){
        return this.status;
    }
}
