package com.dianping.ba.finance.exchange.monitor.api.enums;


public enum ExceptionType {

    DEFAULT(0),

    PP_INIT_IN_RELATION(1),

    PP_PENDING(2),

    PP_FLOW_NOT_FOUND(3),

    PP_EO_NOT_FOUND(4),

    PP_EO_STATUS_INCOMPATIBLE(5),

    PP_EO_MULTI_INIT_OR_PENDING(6),

    PP_EO_MULTI_PAID(7);

    private int type;

    private ExceptionType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static ExceptionType valueOf(int type){
        switch(type) {
            case 1:
                return PP_INIT_IN_RELATION;
            case 2:
                return PP_PENDING;
            case 3:
                return PP_FLOW_NOT_FOUND;
            case 4:
                return PP_EO_NOT_FOUND;
            case 5:
                return PP_EO_STATUS_INCOMPATIBLE;
            case 6:
                return PP_EO_MULTI_INIT_OR_PENDING;
            case 7:
                return PP_EO_MULTI_PAID;
            default:
                return DEFAULT;
        }
    }

    public static String toString(ExceptionType exType){
        switch(exType){
            case PP_INIT_IN_RELATION:
                return "初始状态的付款计划，在关系表中存在";
            case PP_PENDING:
                return "付款计划状态一直为待生成付款单";
            case PP_FLOW_NOT_FOUND:
                return "无法找到付款计划的流水号";
            case PP_EO_NOT_FOUND:
                return "无法找到付款计划对应的付款单";
            case PP_EO_STATUS_INCOMPATIBLE:
                return "付款计划和付款单状态错误";
            case PP_EO_MULTI_INIT_OR_PENDING:
                return "付款单有多条记录是初始或支付中";
            case PP_EO_MULTI_PAID:
                return "付款单有多条记录是支付成功";
            default:
                return "未知错误";
        }

    }

    public int value(){
        return this.type;
    }


}
