package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.ba.finance.exchange.siteweb.constants.OptionConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eric on 2014/5/29.
 */
public class LoadOptionAction extends AjaxBaseAction {

    private Map<Object, Object> option = new HashMap<Object, Object>();
    private int code;

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

}
