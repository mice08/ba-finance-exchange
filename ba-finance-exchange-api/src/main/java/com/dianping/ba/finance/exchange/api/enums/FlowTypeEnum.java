package com.dianping.ba.finance.exchange.api.enums;

/**
 * 交易类型
 * @author junjie.mao
 *
 */

//TODO: refactor to uppercase
public enum FlowTypeEnum {

	/**
	 * 默认
	 */
	DEFAULT(0),
	/**
	 * 进账
	 */
	IN(1),
    /**
     * 出账
     */
    OUT(2);


	private int flowType;

	private FlowTypeEnum(int flowType) {
		this.setFlowType(flowType);
	}

	@Override
	public String toString() {
		switch (flowType) {
			case 1:
				return "进账";
			case 2:
                return "出账";
			default:
				return "默认";
		}
	}

	public  String toString(int flowType) {
		this.setFlowType(flowType);
		return this.toString();
	}

	public void setFlowType(int flowType) {
		this.flowType = flowType;
	}

	public int getFlowType() {
		return flowType;
	}
}
