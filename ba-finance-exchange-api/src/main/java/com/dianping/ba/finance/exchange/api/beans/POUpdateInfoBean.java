package com.dianping.ba.finance.exchange.api.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by noahshen on 14-6-12.
 */
public class POUpdateInfoBean implements Serializable {

    private List<Integer> poIdList;

    private int preStatus;

    private int updateStatus;

    private Date paidDate;

    private String memo;

    private int loginId;

    public POUpdateInfoBean() {
    }

    public List<Integer> getPoIdList() {
        return poIdList;
    }

    public void setPoIdList(List<Integer> poIdList) {
        this.poIdList = poIdList;
    }

    public int getPreStatus() {
        return preStatus;
    }

    public void setPreStatus(int preStatus) {
        this.preStatus = preStatus;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    @Override
    public String toString() {
        return "POUpdateInfoBean{" +
                "poIdList=" + poIdList +
                ", preStatus=" + preStatus +
                ", updateStatus=" + updateStatus +
                ", paidDate=" + paidDate +
                ", memo='" + memo + '\'' +
                ", loginId=" + loginId +
                '}';
    }
}
