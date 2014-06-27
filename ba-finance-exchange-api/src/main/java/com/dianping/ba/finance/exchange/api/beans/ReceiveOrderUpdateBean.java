package com.dianping.ba.finance.exchange.api.beans;

import java.io.Serializable;

/**
 * Created by Eric on 2014/6/11.
 */
public class ReceiveOrderUpdateBean implements Serializable {

	private int reverseRoId;

    private int status;

	private int updateLoginId;

	private String memo;

    public ReceiveOrderUpdateBean() {
    }

	public int getReverseRoId() {
		return reverseRoId;
	}

	public void setReverseRoId(int reverseRoId) {
		this.reverseRoId = reverseRoId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getUpdateLoginId() {
		return updateLoginId;
	}

	public void setUpdateLoginId(int updateLoginId) {
		this.updateLoginId = updateLoginId;
	}

	@Override
	public String toString() {
		return "ReceiveOrderUpdateBean{" +
				"reverseRoId=" + reverseRoId +
				", status=" + status +
				", updateLoginId=" + updateLoginId +
				", memo='" + memo + '\'' +
				'}';
	}
}
