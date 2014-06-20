package com.dianping.ba.finance.exchange.monitor.api.datas;

import java.util.Date;

public class TodoData {
    private int todoId;
    private int eoId;
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

    public int getEoId() {
        return eoId;
    }

    public void setEoId(int eoId) {
        this.eoId = eoId;
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
                ", eoId=" + eoId +
                ", addDate=" + addDate +
                ", status=" + status +
                '}';
    }
}
