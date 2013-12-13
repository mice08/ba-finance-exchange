package com.dianping.ba.finance.exchange.api.enums;

/**
 * Created with IntelliJ IDEA.
 * User: bingqiu.yuan
 * Date: 13-12-13
 * Time: 下午2:39
 * 来源.
 */
public enum SourceType {

        /**
         * 0-默认错误
         */
        Default(0),

        /**
         * 1-付款计划
         */
        PaymentPlan(1),

        /**
         * 2-收款计划
         */
        ReceivePlan(2),

        /**
         * 3- 指令
         */
        ExchangeOrder(3);

        private int sourceType;

        private SourceType(int sourceType){
            this.sourceType = sourceType;
        }

        public int getSourceType() {
            return sourceType;
        }

        public void setSourceType(int sourceType) {
            this.sourceType = sourceType;
        }

    @Override
        public String toString() {
            switch (sourceType) {
                case 1:
                    return "付款计划";
                case 2:
                    return "收款计划";
                case 3:
                    return "指令";
                default:
                    return "错误";
            }
        }

        public static SourceType valueOf(int value){
            switch (value){
                case 1:
                    return PaymentPlan;
                case 2:
                    return ReceivePlan;
                case 3:
                    return ExchangeOrder;
                default:
                    return Default;
            }
        }

    }
