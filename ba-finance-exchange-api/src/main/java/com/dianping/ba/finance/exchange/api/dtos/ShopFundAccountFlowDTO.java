package com.dianping.ba.finance.exchange.api.dtos;

import com.dianping.ba.finance.exchange.api.enums.SourceTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 13-12-13
 * Time: 下午2:36
 * To change this template use File | Settings | File Templates.
 */
public class ShopFundAccountFlowDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * CustomerGlobalId
     */
    private String customerGlobalId;
    /**
     * CompanyGlobalId
     */
    private String companyGlobalId;
    /**
     * 分店号
     */
    private int shopId;
    /**
     * 账户类型：0默认；1团购；2预约预订; 3结婚; 4储值卡
     */
    private int businessType;
    /**
     * 金额（都是正数）
     */
    private BigDecimal flowAmount;
    /**
     * 1:正向 2:负向
     */
    private int flowType;
    /**
     * 源头类型：1.应付2.应收3.交易指令
     */
    private SourceTypeEnum sourceType;

}
