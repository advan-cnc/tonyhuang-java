package com.ad.entity;

public class ResultBody<T> {
    private T data;
    private int code;
    private String message = "";

    public ResultBody() {
    }

    public ResultBody(T data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public ResultBody setData(T data) {
        this.data = data;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ResultBody setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResultBody setMessage(String message) {
        this.message = message;
        return this;
    }
}
