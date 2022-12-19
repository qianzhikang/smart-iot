package com.qzk.common.exception;

/**
 * @Description TODO
 * @Date 2022-12-19-14-38
 * @Author qianzhikang
 */
public class AuthException extends RuntimeException{
    private String msg;
    private Integer code = 1;

    public AuthException() {
    }

    public AuthException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public AuthException(Integer code, String msg) {
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
