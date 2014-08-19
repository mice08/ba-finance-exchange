package com.dianping.ba.finance.exchange.midasreco.api.datas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by will on 14-8-18.
 */
public class InvoiceRecoData implements Serializable {

    private int id;
    private String batchId;
    private BigDecimal invoiceAmount;
    private String bizId;
    private int customerId;
    private int shopId;
    private String bizContent;
    private String invoiceTitle;
    private String invoiceContent;
    private int companyId;
    private String taxNumber;
    private String bankName;
    private String bankAccountNo;
    private String address;
    private String phone;
    private String invoiceTaxNo;
    private Date releaseDate;
    private int invoiceType;
    private Date addTime;
    private Date updateTime;

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
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

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoiceTaxNo() {
        return invoiceTaxNo;
    }

    public void setInvoiceTaxNo(String invoiceTaxNo) {
        this.invoiceTaxNo = invoiceTaxNo;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @Override
    public String toString() {
        return "InvoiceRecoData{" +
                "id=" + id +
                ", batchId='" + batchId + '\'' +
                ", invoiceAmount=" + invoiceAmount +
                ", bizId='" + bizId + '\'' +
                ", customerId=" + customerId +
                ", shopId=" + shopId +
                ", bizContent='" + bizContent + '\'' +
                ", invoiceTitle='" + invoiceTitle + '\'' +
                ", invoiceContent='" + invoiceContent + '\'' +
                ", companyId=" + companyId +
                ", taxNumber='" + taxNumber + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccountNo='" + bankAccountNo + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", invoiceTaxNo='" + invoiceTaxNo + '\'' +
                ", releaseDate=" + releaseDate +
                ", invoiceType=" + invoiceType +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
