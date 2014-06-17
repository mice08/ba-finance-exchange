package com.dianping.ba.finance.exchange.api.enums;

/**
 * 收款类型
 */
public enum CompanyIDName {

	/**
	 * 0-默认错误
	 */
	DEFAULT(0, "错误"),

	/**
     * 1-汉涛
     */
    HAN_TAO(1, "汉涛"),

	/**
     * 2-汉涛上海
     */
    HAN_TAO_SH(2, "汉涛上海"),

	/**
     * 3-汉涛北京
     */
    HAN_TAO_BJ(3, "汉涛北京"),

	/**
     * 4-汉涛广州
     */
    HAN_TAO_GZ(4, "汉涛广州");

    private int companyId;

    private String companyName;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    CompanyIDName(int companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return this.companyName;
    }

	public static CompanyIDName valueOf(int value) {
        for (CompanyIDName company : CompanyIDName.values()) {
            if (company.getCompanyId() == value) {
                return company;
            }
        }
        return DEFAULT;
	}

    public int value(){
        return companyId;
    }
}
