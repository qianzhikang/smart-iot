package com.qzk.common.exception;

/**
 * @Description 自定义异常类
 * @Date 2022-11-21-16-48
 * @Author qianzhikang
 */
public class ApiException extends RuntimeException {
    private String msg;
    private Integer code = 1;

    public ApiException() {
    }

    public ApiException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ApiException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
