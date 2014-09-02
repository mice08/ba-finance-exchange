package com.dianping.ba.finance.exchange.siteweb.action;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.PayCentreReceiveRequestHandleService;
import com.dianping.ba.finance.exchange.api.dtos.PayCentreReceiveRequestDTO;
import com.dianping.ba.finance.exchange.siteweb.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * Created by IntelliJ IDEA.
 * User: yaxiong.cheng
 * Date: 2014/7/31
 * Time: 9:59
 * To change this template use File | Settings | File Templates.
 * 支付中心请求http接口
 */
public class PayCentreReceiveHttpAjaxAction extends WebBaseAction {

    /**
	 * 记录需要监控的业务日志
	 */
	private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger
			("com.dianping.ba.finance.exchange.web.monitor.PayCentreAjaxAction");

	//PayCentreReceiveRequestDTO的字段
	/**
	 * 支付中心唯一交易流水号
	 */
	private String tradeNo;
	/**
	 * 交易类型：1收款；2冲销
	 */
	private int txtType;
	/**
	 * 我司收款银行   汉涛上海：1    汉海上海：8   汉海北京：7   汉海广州：11
	 */
	private int bankid;

	private String amount;
	/**
	 * 收款日期
	 */
	private String receiveDate;

	/**
	 * 对方付款渠道：10是快钱渠道
	 */
	private int paymentChannel;
	/**
	 * 对方付款方式：5是POS机
	 */
	private int payMethod;
	/**
	 * 业务文本（广告：合同号）
	 */
	private String contractNo;

	/**
	 * 原始交易号 冲销必须有
	 */
	private String oriTradeNo;

	private PayCentreReceiveRequestHandleService payCentreReceiveRequestHandleService;


    @Override
    protected String webExecute() throws Exception {
        try {
            PayCentreReceiveRequestDTO payCentreReceiveRequestDTO = buildPayCentreReceiveRequestDTO();
            payCentreReceiveRequestHandleService.handleReceiveRequest(payCentreReceiveRequestDTO);
            return SUCCESS;
        } catch (Exception e) {
            MONITOR_LOGGER.error("severity=[1] PayCentreReceiveHttpAjaxAction.jsonExecute error!", e);
            return ERROR;
        }
    }


    private PayCentreReceiveRequestDTO buildPayCentreReceiveRequestDTO() throws ParseException {
        PayCentreReceiveRequestDTO dtoBean = new PayCentreReceiveRequestDTO();
		if (StringUtils.isBlank(amount)) {
			dtoBean.setReceiveAmount(BigDecimal.ZERO);
		} else {
			dtoBean.setReceiveAmount(new BigDecimal(amount));
		}
		dtoBean.setBizContent(contractNo);
		dtoBean.setBankId(bankid);
		dtoBean.setBusinessType(6);// 支付中心的，广告业务是6
		dtoBean.setOriTradeNo(oriTradeNo);
		dtoBean.setTradeNo(tradeNo);
		dtoBean.setTradeType(txtType);
		dtoBean.setPayChannel(paymentChannel);
		dtoBean.setPayMethod(payMethod);
        //yyyyMMddhhmmss
		dtoBean.setReceiveDate(DateUtil.parseDate(receiveDate, "yyyyMMddhhmmss"));
		return dtoBean;
	}

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public void setTxtType(int txtType) {
        this.txtType = txtType;
    }

    public void setBankid(int bankid) {
        this.bankid = bankid;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public void setPaymentChannel(int paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public void setOriTradeNo(String oriTradeNo) {
        this.oriTradeNo = oriTradeNo;
    }

    public void setPayCentreReceiveRequestHandleService(PayCentreReceiveRequestHandleService payCentreReceiveRequestHandleService) {
		this.payCentreReceiveRequestHandleService = payCentreReceiveRequestHandleService;
	}

}
