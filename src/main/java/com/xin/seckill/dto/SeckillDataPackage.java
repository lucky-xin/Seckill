package com.xin.seckill.dto;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: 将所有的ajax请求返回类型，全部封装成json数据
 * @date 2018-08-10 16:29
 * @Copyright (C)2018 , Luchaoxin
 */
public class SeckillDataPackage<T> {

    //请求是否成功
    private boolean success;

    private T data;

    private String error;

    public SeckillDataPackage(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SeckillDataPackage(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}