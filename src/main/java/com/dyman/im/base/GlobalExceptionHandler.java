package com.dyman.im.base;

import com.dyman.im.constant.RequestResultEnum;
import com.dyman.im.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @Author dyman
 * @Date 2020/1/22
 * @Description 全局异常处理器
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public Result methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e){
        log.error(e.toString());
        Result result = new Result();
        result.setCode(RequestResultEnum.Error.getCode());
        String errorMsg = e.getMessage();
        result.setMessage(StringUtils.isNotBlank(errorMsg) ? e.getCause().getCause().getMessage() : RequestResultEnum.Error.getMsg());
        return result;
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e){
        log.error(e.toString());
        Result result = new Result();
        result.setCode(RequestResultEnum.Error.getCode());
        String errorMsg = e.getMessage();
        result.setMessage(StringUtils.isNotBlank(errorMsg) ? errorMsg : RequestResultEnum.Error.getMsg());
        return result;
    }
}
