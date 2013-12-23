package com.dianping.ba.finance.exchange.api.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 遐
 * Date: 13-12-13
 * Time: 上午11:33
 * To change this template use File | Settings | File Templates.
 */
public class  GenericResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<T> successList;
    private List<T> failList;

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


}
