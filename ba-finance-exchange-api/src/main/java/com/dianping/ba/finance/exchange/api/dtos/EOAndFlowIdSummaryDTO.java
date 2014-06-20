package com.dianping.ba.finance.exchange.api.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: noahshen
 * Date: 14-2-24
 * Time: 下午1:52
 * To change this template use File | Settings | File Templates.
 */
public class EOAndFlowIdSummaryDTO implements Serializable {

    private static final long serialVersionUID = 8872187550686706215L;

    private String bizCode;

    private BigDecimal orderAmount;

    private int status;

    private Date addDate;

    private int flowId;

    private int businessType;

    public EOAndFlowIdSummaryDTO() {
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public int getFlowId() {
        return flowId;
    }

    public void setFlowId(int flowId) {
        this.flowId = flowId;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    @Override
    public String toString() {
        return "EOAndFlowIdSummaryDTO{" +
                "bizCode='" + bizCode + '\'' +
                ", orderAmount=" + orderAmount +
                ", status=" + status +
                ", addDate=" + addDate +
                ", flowId=" + flowId +
                ", businessType=" + businessType +
                '}';
    }
}
