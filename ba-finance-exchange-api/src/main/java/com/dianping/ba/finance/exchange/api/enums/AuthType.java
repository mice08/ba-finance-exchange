package com.dianping.ba.finance.exchange.api.enums;

/**
 * Created by will on 15-3-10.
 */
public enum AuthType {

    /**
     * 0-默认错误
     */
    DEFAULT(0, "错误"),

    /**
     * RSA动态口令
     */
    RSA(1, "RSA动态口令");

    private int type;

    private String description;

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    AuthType(int type, String description) {
        this.type = type;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }

    public static AuthType valueOf(int value){
        for (AuthType type : AuthType.values()) {
            if (type.getType() == value) {
                return type;
            }
        }
        return DEFAULT;
    }

    public int value(){
        return type;
    }
}
