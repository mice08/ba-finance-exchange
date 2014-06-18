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

    public String loadBusinessTypeOption() {
        option.putAll(OptionConstant.BUSINESSTYPE_OPTION);
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadReceiveOrderPayChannelOption() {
        ReceiveOrderPayChannel[] channels = ReceiveOrderPayChannel.values();
        for (ReceiveOrderPayChannel channel : channels) {
            option.put(channel.getChannel(), channel.getDescription());
        }
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadReceiveTypeOption() {
        ReceiveType[] receiveTypes = ReceiveType.values();
        for (ReceiveType type : receiveTypes) {
            option.put(type.getReceiveType(), type.getDescription());
        }
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadReceiveBankOption() {
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
