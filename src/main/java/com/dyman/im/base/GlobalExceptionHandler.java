package com.dyman.im.base;

import com.dyman.im.constant.RequestResultEnum;
import com.dyman.im.entity.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author dyman
 * @Date 2020/1/22
 * @Description 全局异常处理器
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e){
        Result result = new Result();
        result.setCode(RequestResultEnum.Error.getCode());
        String errorMsg = e.getMessage();
        result.setMessage(StringUtils.isNotBlank(errorMsg) ? errorMsg : RequestResultEnum.Error.getMsg());
        return result;
    }
}
