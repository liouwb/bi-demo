package com.liouxb.bi.exception;


import com.liouxb.bi.entity.resp.BasicResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author liouwb
 */
@RestControllerAdvice
public class ExceptionConfig {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 请求参数校验不通过
     */
    @ExceptionHandler(RequestParamsValidException.class)
    @ResponseBody
    public BasicResp requestParameterException(RequestParamsValidException e) {
        log.error(e.getMessage());
        return new BasicResp("200", "500", e.getMessage().split(":")[0], "");
    }

    /**
     * 404异常处理
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public BasicResp requestParameterException(NoHandlerFoundException e) {
        log.error(e.getMessage());
        return new BasicResp("200", "500", e.getMessage().split(":")[0], "");
    }

    /**
     * 抛出所有未处理的异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BasicResp exception(Exception e) {
        log.error(e.getMessage());
        return new BasicResp("200", "500", e.getMessage().split(":")[0], "");
    }

}
