package com.dianping.ba.finance.exchange.siteweb.constants;

import com.dianping.ba.finance.exchange.api.enums.CompanyIDName;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 2014/6/11.
 */
public class PermissionConstant {

    public final static Map<Integer, List<String>> PERMISSION_CITY_OPTION = new HashMap<Integer, List<String>>() {
        {
            put(50040, Arrays.asList(CompanyIDName.HAN_TAO.toString(), CompanyIDName.HAN_HAI_SH.toString()));
            put(50041, Arrays.asList(CompanyIDName.HAN_TAO.toString(), CompanyIDName.HAN_HAI_SH.toString()));
            put(50042, Arrays.asList(CompanyIDName.HAN_HAI_BJ.toString()));
            put(50043, Arrays.asList(CompanyIDName.HAN_HAI_BJ.toString()));
            put(50044, Arrays.asList(CompanyIDName.HAN_HAI_GZ.toString()));
            put(50045, Arrays.asList(CompanyIDName.HAN_HAI_GZ.toString()));
        }
    };
}
