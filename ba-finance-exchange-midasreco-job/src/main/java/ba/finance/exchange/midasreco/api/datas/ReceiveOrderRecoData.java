package ba.finance.exchange.midasreco.api.datas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by will on 14-8-18.
 */
public class ReceiveOrderRecoData implements Serializable {

    private int roMId;

    private int roId;

    private int customerId;

    private int shopId;

    private int businessType;

    private BigDecimal receiveAmount;

    private Date receiveTime;

    private int payChannel;

    private int receiveType;

    private String bizContent;

    private String tradeNo;

    private int bankID;

    private Date addTime;

    private int addLoginId;

    private Date updateTime;

    private int updateLoginId;

    private String memo;

    public int getRoId() {
        return roId;
    }

    public void setRoId(int roId) {
        this.roId = roId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public BigDecimal getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(BigDecimal receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getBankID() {
        return bankID;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public int getAddLoginId() {
        return addLoginId;
    }

    public void setAddLoginId(int addLoginId) {
        this.addLoginId = addLoginId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getUpdateLoginId() {
        return updateLoginId;
    }

    public void setUpdateLoginId(int updateLoginId) {
        this.updateLoginId = updateLoginId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "ReceiveOrderData{" +
                "roMId=" + roId +
                "roId=" + roId +
                ", customerId=" + customerId +
                ", shopId=" + shopId +
                ", businessType=" + businessType +
                ", receiveAmount=" + receiveAmount +
                ", receiveTime=" + receiveTime +
                ", payChannel=" + payChannel +
                ", receiveType=" + receiveType +
                ", bizContent='" + bizContent + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", bankID=" + bankID +
                ", addTime=" + addTime +
                ", addLoginId=" + addLoginId +
                ", updateTime=" + updateTime +
                ", updateLoginId=" + updateLoginId +
                ", memo='" + memo + '\'' +
                '}';
    }

    public int getRoMId() {
        return roMId;
    }

    public void setRoMId(int roMId) {
        this.roMId = roMId;
    }
}
