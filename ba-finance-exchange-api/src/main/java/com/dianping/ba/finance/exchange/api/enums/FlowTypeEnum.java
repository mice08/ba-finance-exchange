package com.dianping.ba.finance.exchange.api.enums;

/**
 * 交易类型
 * @author junjie.mao
 *
 */
public enum FlowTypeEnum {

	/**
	 * 默认
	 */
	Default(0),
	/**
	 * 充值
	 */
	Input(1),
    /**
     * 退款
     */
    Output(2);


	private int flowType;

	private FlowTypeEnum(int flowType) {
		this.setFlowType(flowType);
	}

	@Override
	public String toString() {
		switch (flowType) {
			case 1:
				return "正向";
			case 2:
                return "负向";
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
