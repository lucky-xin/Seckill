package com.xin.seckill.enums;

/*
 * @author Luchaoxin
 * @version V1.0
 * @Description: 性别menu类型
 * @date 2018-08-11 21:45
 * @Copyright (C)2018 , Luchaoxin
 */
public enum Sex {

    MALE(0, "男"),

    FEMALE(1, "女");

    private int code;
    private String cnValue;

    Sex(int code, String cnValue) {
        this.code = code;
        this.cnValue = cnValue;
    }

    Sex(int code) {
        if (0 == code) {
            this.code = code;
            this.cnValue = "男";
        } else {
            this.code = 1;
            this.cnValue = "女";
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCnValue() {
        return cnValue;
    }

    public void setCnValue(String cnValue) {
        this.cnValue = cnValue;
    }

    public static Sex getSex(int code) {
        for (Sex value : Sex.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }

    public static String getCnValue(int code) {
        for (Sex value : Sex.values()) {
            if (value.getCode() == code) {
                return value.getCnValue();
            }
        }
        return null;
    }

    public static int getCode(String cnValue) {
        if ("男".equals(cnValue)) {
            return MALE.getCode();
        }

        if ("女".equals(cnValue)) {
            return FEMALE.getCode();
        }
        return -1;
    }

}
