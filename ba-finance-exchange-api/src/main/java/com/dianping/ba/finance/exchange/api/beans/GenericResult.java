package com.dianping.ba.finance.exchange.api.beans;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 上午11:33
 * To change this template use File | Settings | File Templates.
 */
public class  GenericResult<T>{

    private List<T> successList;
    private List<T> failList;
    private List<T> unprocessedList;

    public List<T> getSuccessList() {
        return successList;
    }

    public void setSuccessList(List<T> successList) {
        this.successList = successList;
    }

    public List<T> getFailList() {
        return failList;
    }

    public void setFailList(List<T> failList) {
        this.failList = failList;
    }

    public List<T> getUnprocessedList() {
        return unprocessedList;
    }

    public void setUnprocessedList(List<T> unprocessedList) {
        this.unprocessedList = unprocessedList;
    }


}
