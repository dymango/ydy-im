package com.dyman.im.entity;

import com.dyman.im.constant.RequestResultEnum;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 **/
@Service
public class Response<T>{

    public Result<T> success()
    {
        return new Result<T>(RequestResultEnum.SUCCESS.getMsg(), RequestResultEnum.SUCCESS.getCode());
    }

    public Result<T> success(T t)
    {
        return new Result<T>(RequestResultEnum.SUCCESS.getMsg(), RequestResultEnum.SUCCESS.getCode(), t);
    }

    public Result<T> success(T t, String message)
    {
        return new Result<T>(message, RequestResultEnum.SUCCESS.getCode(), t);
    }

    public Result<T> error(T t)
    {
        return new Result<T>(RequestResultEnum.Error.getMsg(), RequestResultEnum.Error.getCode(), t);
    }

    public Result<T> error()
    {
        return new Result<T>(RequestResultEnum.Error.getMsg(), RequestResultEnum.Error.getCode());
    }

    public Result<T> error(T t, String message)
    {
        return new Result<T>(message, RequestResultEnum.Error.getCode(), t);
    }
}
