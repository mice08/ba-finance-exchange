package com.dianping.ba.finance.exchange.receivemonitor.api.datas;

import java.util.Date;

public class TodoData {
    private int todoId;
    private int roId;
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

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

	public int getRoId() {
		return roId;
	}

	public void setRoId(int roId) {
		this.roId = roId;
	}

	@Override
    public String toString() {
        return "MonitorTodoData{" +
                "todoId=" + todoId +
                ", roId=" + roId +
                ", addDate=" + addDate +
                ", status=" + status +
                '}';
    }
}
