package com.dianping.ba.finance.exchange.paymonitor.api.datas;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by adam.huang on 2014/8/7.
 */
public class MonitorTodoData implements Serializable{
    private int todoId;
    private int ppId;
    private Date addDate;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public int getPpId() {
        return ppId;
    }

    public void setPpId(int ppId) {
        this.ppId = ppId;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    @Override
    public String toString() {
        return "MonitorTodoData{" +
                "todoId=" + todoId +
                ", ppId=" + ppId +
                ", addDate=" + addDate +
                ", status=" + status +
                '}';
    }
}
