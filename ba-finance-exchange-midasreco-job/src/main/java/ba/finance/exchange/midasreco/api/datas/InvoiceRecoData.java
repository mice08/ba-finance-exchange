package ba.finance.exchange.midasreco.api.datas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by will on 14-8-18.
 */
public class InvoiceRecoData implements Serializable {

    private int invoiceMId;
    private int invoiceId;
    private String bizId;
    private int customerId;
    private int shopId;
    private Date releaseDate;
    private String invoiceTitle;
    private String invoiceContent;
    private String invoiceTaxNo;
    private int companyId;
    private String bizContent;
    private String taxNumber;
    private String bankName;
    private String bankAccountNo;
    private String address;
    private String phone;
    private int invoiceType;
    private BigDecimal invoiceAmount;
    private String reverseTaxNo;
    private int businessType;

    private Date addTime;

    private int addLoginId;

    private Date updateTime;

    private int updateLoginId;

    private String memo;

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

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public int getAddLoginId() {
        return addLoginId;
    }

    public void setAddLoginId(int addLoginId) {
        this.addLoginId = addLoginId;
    }

    public int getUpdateLoginId() {
        return updateLoginId;
    }

    public void setUpdateLoginId(int updateLoginId) {
        this.updateLoginId = updateLoginId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getReverseTaxNo() {
        return reverseTaxNo;
    }

    public void setReverseTaxNo(String reverseTaxNo) {
        this.reverseTaxNo = reverseTaxNo;
    }

    public String getInvoiceTaxNo() {
        return invoiceTaxNo;
    }

    public void setInvoiceTaxNo(String invoiceTaxNo) {
        this.invoiceTaxNo = invoiceTaxNo;
    }

    @Override
    public String toString() {
        return "InvoiceData{" +
                "invoiceMId=" + invoiceMId +
                "invoiceId=" + invoiceId +
                ", bizId='" + bizId + '\'' +
                ", customerId=" + customerId +
                ", shopId=" + shopId +
                ", releaseDate=" + releaseDate +
                ", addLoginId=" + addLoginId +
                ", updateLoginId=" + updateLoginId +
                ", invoiceTitle='" + invoiceTitle + '\'' +
                ", invoiceContent='" + invoiceContent + '\'' +
                ", invoiceTaxNo='" + invoiceTaxNo + '\'' +
                ", companyId=" + companyId +
                ", bizContent='" + bizContent + '\'' +
                ", taxNumber='" + taxNumber + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccountNo='" + bankAccountNo + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", invoiceType=" + invoiceType +
                ", invoiceAmount=" + invoiceAmount +
                ", reverseTaxNo='" + reverseTaxNo + '\'' +
                ", businessType=" + businessType +
                ", memo='" + memo + '\'' +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public int getInvoiceMId() {
        return invoiceMId;
    }

    public void setInvoiceMId(int invoiceMId) {
        this.invoiceMId = invoiceMId;
    }
}
