package com.dianping.ba.finance.exchange.siteweb.action.ajax;

import com.dianping.ba.finance.authentication.api.AuthenticationService;
import com.dianping.ba.finance.authentication.api.enums.AuthType;

import java.util.Map;
/**
 * Created by will on 15-3-13.
 */
public class SendSMSAuthCodeAjaxAction extends AjaxBaseAction {

    private AuthenticationService authenticationService;
    @Override
    protected void jsonExecute() throws Exception {
        authenticationService.send(getWorkNo(), AuthType.SMS.getCode());
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public Map<String, Object> getMsg() {
        return null;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
}
