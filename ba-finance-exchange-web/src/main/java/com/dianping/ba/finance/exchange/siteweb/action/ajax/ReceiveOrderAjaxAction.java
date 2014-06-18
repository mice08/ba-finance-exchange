package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.avatar.log.AvatarLogger;
import com.dianping.avatar.log.AvatarLoggerFactory;
import com.dianping.ba.finance.exchange.api.ReceiveOrderService;
import com.dianping.ba.finance.exchange.api.datas.ReceiveOrderData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderStatus;
import com.dianping.ba.finance.exchange.api.enums.ReceiveType;
import com.dianping.ba.finance.exchange.siteweb.util.DateUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 *
 */
public class ReceiveOrderAjaxAction extends AjaxBaseAction {

    /**
     * 记录需要监控的业务日志
     */
    private static final AvatarLogger MONITOR_LOGGER = AvatarLoggerFactory.getLogger("com.dianping.ba.finance.exchange.web.monitor.ReceiveOrderAjaxAction");

    private int customerId;

    private int businessType;

    private BigDecimal receiveAmount = BigDecimal.ZERO;

    private String bankReceiveTime;

    private int payChannel;

    private int receiveType;

    private String bizContent;

    private int bankId;

    private String memo;

    private int code;

    private Map<String, Object> msg = Maps.newHashMap();

    private ReceiveOrderService receiveOrderService;

    @Override
    protected void jsonExecute() throws Exception {

    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public Map<String, Object> getMsg() {
        return msg;
    }

    /**
     * 手工录入付款单
     * @return
     * @throws Exception
     */
	public String createReceiveOrderManually() throws Exception {
		try {
            int loginId = getLoginId();
            StringBuilder sb = new StringBuilder();
            if (!isInputValid(sb)) {
                msg.put(msgKey, sb.toString());
                code = ERROR_CODE;
                return SUCCESS;
            }

            ReceiveOrderData receiveOrderData = buildReceiveOrderData(loginId);
            receiveOrderService.createReceiveOrder(receiveOrderData);
            code = SUCCESS_CODE;
            return SUCCESS;
		} catch (Exception e) {
			MONITOR_LOGGER.error("severity=[1] ReceiveOrderAjaxAction.createReceiveOrderManually error!", e);
            code = ERROR_CODE;
			return SUCCESS;
		}
	}

    private boolean isInputValid(StringBuilder sb) {
        if (customerId == 0) {
            sb.append("请填写客户名");
            return false;
        }
        if (businessType == BusinessType.DEFAULT.value()) {
            sb.append("请选择产品线");
            return false;
        }
        if (receiveAmount == null || BigDecimal.ZERO.compareTo(receiveAmount) >= 0) {
            sb.append("请正确填写收款金额");
            return false;
        }
        if (bankReceiveTime!=null && !DateUtil.isValidDate(bankReceiveTime)) {
            sb.append("请填写正确的收款日期");
            return false;
        }
        if (payChannel == ReceiveOrderPayChannel.DEFAULT.value()) {
            sb.append("请填写收款方式");
            return false;
        }
        if (receiveType == ReceiveType.DEFAULT.value()) {
            sb.append("请填写业务类型");
            return false;
        }
        if (StringUtils.isEmpty(bizContent)) {
            sb.append("请填写业务ID");
            return false;
        }
        return true;
    }


    public ReceiveOrderData buildReceiveOrderData(int loginId) throws ParseException {
        ReceiveOrderData roData = new ReceiveOrderData();
        roData.setCustomerId(customerId);
        roData.setBusinessType(businessType);
        roData.setReceiveAmount(receiveAmount);
        Date bankReceiveTimeDate = DateUtil.isValidDate(bankReceiveTime) ? DateUtil.formatDate(bankReceiveTime, false) : null;
        roData.setBankReceiveTime(bankReceiveTimeDate);
        roData.setPayChannel(payChannel);
        roData.setReceiveType(receiveType);
        roData.setBizContent(bizContent);
        roData.setBankID(bankId);
        roData.setMemo(memo);
        roData.setStatus(ReceiveOrderStatus.CONFIRMED.value());
        roData.setAddLoginId(loginId);
        roData.setUpdateLoginId(loginId);
        return roData;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public void setBankReceiveTime(String bankReceiveTime) {
        this.bankReceiveTime = bankReceiveTime;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setReceiveOrderService(ReceiveOrderService receiveOrderService) {
        this.receiveOrderService = receiveOrderService;
    }
}
