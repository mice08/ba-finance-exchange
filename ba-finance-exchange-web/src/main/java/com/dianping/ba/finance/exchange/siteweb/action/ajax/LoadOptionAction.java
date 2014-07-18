package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.ba.finance.exchange.api.ReceiveBankService;
import com.dianping.ba.finance.exchange.api.datas.ReceiveBankData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.CompanyIDName;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel;
import com.dianping.ba.finance.exchange.api.enums.ReceiveType;
import com.dianping.ba.finance.exchange.siteweb.constants.OptionConstant;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Eric on 2014/5/29.
 */
public class LoadOptionAction extends AjaxBaseAction {

    // 使用TreeMap，保证页面显示的顺序
    private Map<Object, Object> option = new TreeMap<Object, Object>();
    private int code;

    private int businessType;

    private ReceiveBankService receiveBankService;

    public String loadPOStatusOption() {
        option.putAll(OptionConstant.POSTATUS_OPTION);
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadROStatusOption(){
        option.putAll(OptionConstant.ROSTATUS_OPTION);
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadBusinessTypeOption() {
        option.putAll(OptionConstant.BUSINESSTYPE_OPTION);
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadProductLionOption() {
        option.putAll(OptionConstant.PRODUCTLINE_OPTION);
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadReceiveOrderPayChannelOption() {
        ReceiveOrderPayChannel[] channels = ReceiveOrderPayChannel.values();
        for (ReceiveOrderPayChannel channel : channels) {
            if (channel.getChannel() == 0) {
                option.put(channel.getChannel(), "请选择收款方式");
            } else  if (!(channel.getChannel() == ReceiveOrderPayChannel.CREDIT_VOUCHER.value())){
                option.put(channel.getChannel(), channel.getDescription());
            }
        }
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadReceiveTypeOption() {
        ReceiveType[] receiveTypes = ReceiveType.values();
        for (ReceiveType type : receiveTypes) {
            if (type.getReceiveType() == 0 && receiveTypes.length > 1) {
                option.put(type.getReceiveType(), "请选择业务类型");
            } else {
                option.put(type.getReceiveType(), type.getDescription());
            }
        }
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadReceiveTypeOptionByPL() {

        if (businessType == BusinessType.DEFAULT.value()) {
            code = ERROR_CODE;
            return SUCCESS;
        }
        if (businessType == BusinessType.GROUP_PURCHASE.value()) {
            option.put(ReceiveType.DEFAULT.value(), "请选择业务类型");
            option.put(ReceiveType.TG_SHELVING_FEE.value(), ReceiveType.TG_SHELVING_FEE.getDescription());
            option.put(ReceiveType.TG_SECURITY_DEPOSIT.value(), ReceiveType.TG_SECURITY_DEPOSIT.getDescription());
            option.put(ReceiveType.TG_GUARANTEE.value(), ReceiveType.TG_GUARANTEE.getDescription());
            option.put(ReceiveType.TG_EXTRA_FEE.value(), ReceiveType.TG_EXTRA_FEE.getDescription());
            option.put(ReceiveType.TG_COMPENSATION.value(), ReceiveType.TG_COMPENSATION.getDescription());
            option.put(ReceiveType.TG_SUPER_REFUND.value(), ReceiveType.TG_SUPER_REFUND.getDescription());
            option.put(ReceiveType.TG_REBATE_MANUALLY.value(), ReceiveType.TG_REBATE_MANUALLY.getDescription());
        } else if (businessType == BusinessType.ADVERTISEMENT.value()) {
            option.put(ReceiveType.AD_FEE.value(), ReceiveType.AD_FEE.getDescription());
        }
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadReceiveTypeOptionInQuery() {

        option.put(ReceiveType.DEFAULT.value(), "请选择业务类型");
        if (businessType == BusinessType.DEFAULT.value()) {
            code = ERROR_CODE;
            return SUCCESS;
        }
        if (businessType == BusinessType.GROUP_PURCHASE.value()) {
            option.put(ReceiveType.TG_SHELVING_FEE.value(), ReceiveType.TG_SHELVING_FEE.getDescription());
            option.put(ReceiveType.TG_SECURITY_DEPOSIT.value(), ReceiveType.TG_SECURITY_DEPOSIT.getDescription());
            option.put(ReceiveType.TG_GUARANTEE.value(), ReceiveType.TG_GUARANTEE.getDescription());
            option.put(ReceiveType.TG_EXTRA_FEE.value(), ReceiveType.TG_EXTRA_FEE.getDescription());
            option.put(ReceiveType.TG_COMPENSATION.value(), ReceiveType.TG_COMPENSATION.getDescription());
            option.put(ReceiveType.TG_SUPER_REFUND.value(), ReceiveType.TG_SUPER_REFUND.getDescription());
            option.put(ReceiveType.TG_REBATE_MANUALLY.value(), ReceiveType.TG_REBATE_MANUALLY.getDescription());
        } else if (businessType == BusinessType.ADVERTISEMENT.value()) {
            option.put(ReceiveType.AD_FEE.value(), ReceiveType.AD_FEE.getDescription());
        }
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadReceiveBankOption() {
        option.put(0, "请选择收款银行账户");
        if (businessType == BusinessType.DEFAULT.value()) {
            code = ERROR_CODE;
            return SUCCESS;
        }
        List<ReceiveBankData> receiveBankDataList = receiveBankService.findAllReceiveBank();

        for (ReceiveBankData receiveBankData : receiveBankDataList) {
            if (receiveBankData.getBusinessType() == businessType) {
                CompanyIDName companyIDName = CompanyIDName.valueOf(receiveBankData.getCompanyId());
                if (companyIDName != null) {
                    option.put(receiveBankData.getBankId(), companyIDName.getCompanyName());
                }
            }
        }
        if (option.size() == 2) {
            option.remove(0);
        }
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadReceiveBankOptionInQuery() {
        option.put(0, "请选择收款银行账户");
        if (businessType == BusinessType.DEFAULT.value()) {
            code = ERROR_CODE;
            return SUCCESS;
        }
        List<ReceiveBankData> receiveBankDataList = receiveBankService.findAllReceiveBank();

        for (ReceiveBankData receiveBankData : receiveBankDataList) {
            if (receiveBankData.getBusinessType() == businessType) {
                CompanyIDName companyIDName = CompanyIDName.valueOf(receiveBankData.getCompanyId());
                if (companyIDName != null) {
                    option.put(receiveBankData.getBankId(), companyIDName.getCompanyName());
                }
            }
        }
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadAllReceiveBankOption() {
        option.put(0, "请选择收款银行账户");
        List<ReceiveBankData> receiveBankDataList = receiveBankService.findAllReceiveBank();
        for (ReceiveBankData receiveBankData : receiveBankDataList) {
            CompanyIDName companyIDName = CompanyIDName.valueOf(receiveBankData.getCompanyId());
            if (companyIDName != null) {
                BusinessType businessTypeEnum = BusinessType.valueOf(receiveBankData.getBusinessType());
                String name = String.format("%s(%s)", companyIDName.getCompanyName(), businessTypeEnum.toString());
                option.put(receiveBankData.getBankId(), name);
            }
        }
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    @Override
    protected void jsonExecute() throws Exception {

    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public Map<String, Object> getMsg() {
        return null;
    }

    public Map<Object, Object> getOption() {
        return this.option;
    }

    public void setReceiveBankService(ReceiveBankService receiveBankService) {
        this.receiveBankService = receiveBankService;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }
}
