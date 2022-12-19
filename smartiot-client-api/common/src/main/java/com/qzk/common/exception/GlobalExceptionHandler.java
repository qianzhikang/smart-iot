package com.qzk.common.exception;

import com.qzk.common.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.bind.ValidationException;

/**
 * @Description TODO
 * @Date 2022-11-21-16-54
 * @Author qianzhikang
 */
@RestControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public RestResult apiException(ApiException apiException){
        return new RestResult().error(apiException.getMsg());
    }

    /**
     * 异常校验类型1
     * @param exception
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public RestResult validType1Exception(MethodArgumentNotValidException exception){
        log.error("error by {}",exception.getFieldErrors().get(0).getDefaultMessage());
        return new RestResult().error(exception.getFieldErrors().get(0).getDefaultMessage());
    }

    /**
     * 异常校验类型2
     * @param exception
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public RestResult validType2Exception(BindException exception){
        log.error("error by {}",exception.getFieldErrors().get(0).getDefaultMessage());
        return new RestResult().error(exception.getFieldErrors().get(0).getDefaultMessage());
    }

    /**
     * 异常校验类型3
     * @param exception
     * @return
     */
    @ExceptionHandler(value = ValidationException.class)
    public RestResult validType3Exception(ValidationException exception){
        log.error("error by {}",exception.getMessage());
        return new RestResult().error(exception.getMessage());
    }

    /**
     * 认证异常
     * @param exception 异常
     * @return
     */
    @ExceptionHandler(value = AuthException.class)
    public RestResult authException(AuthException exception){
        log.error("error by {}",exception.getMessage());
        return new RestResult().error(exception.getMessage());
    }



    @ExceptionHandler(Exception.class)
    public RestResult otherException(Exception exception){

        log.error("error by {}",exception.getMessage());
        return new RestResult().error("系统发生未知错误，请联系管理员处理！");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RestResult requestMethodException(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException){
        log.error("请求方式错误");
        return new RestResult().error("请注意你的请求方式噢！");
    }

}
