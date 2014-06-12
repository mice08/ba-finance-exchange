package com.dianping.ba.finance.exchange.siteweb.beans;

import java.math.BigDecimal;

/**
 * Description：
 * User: liufeng.ren
 * Date: 14-6-11
 * Time: 下午1:51
 */
public class PayOrderExportBean {
	//业务参考号，就是orderId
	private int orderId;

	//收款人编号，默认空
	//1-行内转帐（只允许对公）
	//2-跨行转帐 （对私或对公）
	private int orderType = 1;

	//网银交易流水号
	private String txserNo="";

	//流水号
	private String compressTxserNo="";

	//一般不输入则默认生成系统凭证号，客户也可自制8位凭证号
	private String evouchNo ="";

	//企业客户号-财务提供
	private String custId = "2200099797";

	//业务种类
	private String busitype = "";

	//业务种类
	private String bussCode = "";

	//批量导入制单标志
	private String batchFlag = "";

	//交易日期
	private String txDate = "";

	//交易时间
	private String txTime = "";

	//预约标志（必须填0）
	private String bookFlag = "";

	//收款人帐号
	private String bankAccountNo;
	//收款人名称
	private String bankAccountName;
	//收方开户支行
	private String bankName;
	//收款人所在省
	private String bankProvince;
	//收款人所在市
	private String bankCity;
	//收方邮件地址，默认空
	private String debitSideEmail = "";
	//币种，默认“人民币”
	private String currency = "人民币";
	//付款分行，默认“上海”
	private String payerBranchBank = "上海";
	//结算方式，默认“普通”
	private String settleType = "普通";
	//付方帐号，默认“121909245310505”
	private String payerAccountNo = "121909245310505";
	//期望日，默认当天
	private String expectedDate;
	//期望时间，默认空
	private String expectedTime = "";
	//用途，期望“合同号+商户名+大众点评”，暂时简单处理，默认“大众点评”
	private String use = "大众点评网";
	//金额
	private BigDecimal orderAmount;
	//收方联行号，默认空
	private String debitSideBankNo = "";
	//收方银行，默认空
	private String debitSideBankName = "";
	//业务摘要，默认‘大众点评网储值卡’,以后团购迁移过来后会改掉
	private String businessSummary = "大众点评网储值卡";

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
}
