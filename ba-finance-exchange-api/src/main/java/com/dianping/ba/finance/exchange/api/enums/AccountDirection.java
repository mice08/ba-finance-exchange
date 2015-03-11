package com.dianping.ba.finance.exchange.api.enums;

/**
 * Created by will on 15-3-9.
 */
public enum AccountDirection {

    /**
     * 异常
     */
    DEFAULT(0),
    /**
     * 1-正向
     */
    POSITIVE(1),
    /**
     * 2-负向
     */
    NEGATIVE(2);

    private int direction;

    private AccountDirection(int direction) {
        this.setDirection(direction);
    }

    public static AccountDirection valueOf(int value) {
        switch (value) {
            case 1:
                return POSITIVE;
            case 2:
                return NEGATIVE;
            default:
                return DEFAULT;
        }
    }

    @Override
    public String toString() {
        switch (direction) {
            case 1:
                return "正向";
            case 2:
                return "负向";
            default:
                return "错误";
        }
    }

    public int value(){
        return direction;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
