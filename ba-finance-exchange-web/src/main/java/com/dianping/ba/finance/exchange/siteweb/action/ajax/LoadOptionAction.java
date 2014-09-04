package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.ba.finance.exchange.api.ReceiveBankService;
import com.dianping.ba.finance.exchange.api.datas.ReceiveBankData;
import com.dianping.ba.finance.exchange.api.enums.BusinessType;
import com.dianping.ba.finance.exchange.api.enums.CompanyIDName;
import com.dianping.ba.finance.exchange.api.enums.ReceiveOrderPayChannel;
import com.dianping.ba.finance.exchange.api.enums.ReceiveType;
import com.dianping.ba.finance.exchange.siteweb.constants.OptionConstant;
import com.dianping.ba.finance.exchange.siteweb.constants.PermissionConstant;
import com.dianping.finance.common.util.LionConfigUtils;
import com.dianping.finance.gabriel.impl.GabrielService;

import java.util.*;

/**
 * Created by Eric on 2014/5/29.
 */
public class LoadOptionAction extends AjaxBaseAction {

    // 使用TreeMap，保证页面显示的顺序
    private Map<Object, Object> option = new TreeMap<Object, Object>();
    private int code;

    private int businessType;

    private ReceiveBankService receiveBankService;
    private GabrielService gabrielService;

    public String loadPOStatusOption() {
        option.putAll(OptionConstant.POSTATUS_OPTION);
        msg.put("option", option);
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadROStatusOption(){
        option.putAll(OptionConstant.ROSTATUS_OPTION);
        msg.put("option", option);
        code = SUCCESS_CODE;
        return SUCCESS;
    }

	public String loadRNStatusOption(){
		option.putAll(OptionConstant.RNSTATUS_OPTION);
        msg.put("option", option);
		code = SUCCESS_CODE;
		return SUCCESS;
	}

    public String loadBusinessTypeOption() {
        option.putAll(OptionConstant.BUSINESSTYPE_OPTION);
        msg.put("option", option);
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadProductLionOption() {
        option.putAll(OptionConstant.PRODUCTLINE_OPTION);
        msg.put("option", option);
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadReceiveOrderPayChannelOption() {
        ReceiveOrderPayChannel[] channels = ReceiveOrderPayChannel.values();
        for (ReceiveOrderPayChannel channel : channels) {
            if (channel.getChannel() == 0) {
                option.put(channel.getChannel(), "全部");
            } else  if (!(channel.getChannel() == ReceiveOrderPayChannel.CREDIT_VOUCHER.value())){
                option.put(channel.getChannel(), channel.getDescription());
            }
        }
        msg.put("option", option);
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadReceiveTypeOption() {
        ReceiveType[] receiveTypes = ReceiveType.values();
        for (ReceiveType type : receiveTypes) {
            if (type.getReceiveType() == 0 && receiveTypes.length > 1) {
                option.put(type.getReceiveType(), "全部");
            } else {
                option.put(type.getReceiveType(), type.getDescription());
            }
        }
        msg.put("option", option);
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadReceiveTypeOptionByPL() {

        if (businessType == BusinessType.DEFAULT.value()) {
            code = ERROR_CODE;
            return SUCCESS;
        }
        if (businessType == BusinessType.GROUP_PURCHASE.value()) {
            option.put(ReceiveType.DEFAULT.value(), "全部");
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
        msg.put("option", option);
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadReceiveTypeOptionInQuery() {

        option.put(ReceiveType.DEFAULT.value(), "全部");
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
        msg.put("option", option);
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public String loadReceiveBankOption() {
        option.put(0, "全部");
        if (businessType == BusinessType.DEFAULT.value()) {
            code = ERROR_CODE;
            return SUCCESS;
        }
        List<ReceiveBankData> receiveBankDataList = receiveBankService.findAllReceiveBank();

        for (ReceiveBankData receiveBankData : receiveBankDataList) {
            if (receiveBankData.getBusinessType() == businessType) {
                CompanyIDName companyIDName = CompanyIDName.valueOf(receiveBankData.getCompanyId());
                if (needShowOption(companyIDName)) {
                    option.put(receiveBankData.getBankId(), companyIDName.getCompanyName());
                }
            }
        }
        if (option.size() == 2) {
            option.remove(0);
        }
        msg.put("option", option);
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    private boolean needShowOption(CompanyIDName companyIDName) {
        Set<String> allowedCompanyNameSet = findAllowedCompanyByPermission();
        String useCityPermission = LionConfigUtils.getProperty("ba-finance-exchange-web.UseCityPermission","true");
        return companyIDName != null && (useCityPermission.equals("false") || useCityPermission.equals("true") && allowedCompanyNameSet.contains(companyIDName.getCompanyName()));
    }

    public String loadReceiveBankOptionInQuery() {
        option.put(0, "全部");
        if (businessType == BusinessType.DEFAULT.value()) {
            code = ERROR_CODE;
            return SUCCESS;
        }
        List<ReceiveBankData> receiveBankDataList = receiveBankService.findAllReceiveBank();

        for (ReceiveBankData receiveBankData : receiveBankDataList) {
            if (receiveBankData.getBusinessType() == businessType) {
                CompanyIDName companyIDName = CompanyIDName.valueOf(receiveBankData.getCompanyId());
                if (needShowOption(companyIDName)) {
                    option.put(receiveBankData.getBankId(), companyIDName.getCompanyName());
                }
            }
        }

        if (option.size() == 2) {
            option.remove(0);
        }

        msg.put("option", option);
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
        msg.put("option", option);
        code = SUCCESS_CODE;
        return SUCCESS;
    }

    public Set<String> findAllowedCompanyByPermission(){
        Set<String> allowedCompanySet = new HashSet<String>();
        List<Integer> permissionIdList = gabrielService.findAllPermissionIdListByLoginId(adminLoginId());
        for (Integer permissionId : permissionIdList){
            if (PermissionConstant.PERMISSION_CITY_OPTION.containsKey(permissionId)){
                allowedCompanySet.addAll(PermissionConstant.PERMISSION_CITY_OPTION.get(permissionId));
            }
        }
        return allowedCompanySet;
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

    public void setGabrielService(GabrielService gabrielService) {
        this.gabrielService = gabrielService;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }
}
