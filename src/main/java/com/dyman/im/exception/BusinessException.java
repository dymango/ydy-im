package com.dyman.im.exception;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author dyman
 * @Date 2020/1/22
 * @Description 业务异常
 **/
@Validated
public class BusinessException extends RuntimeException {

    public BusinessException()
    {
        super();
    }

    public BusinessException(@NotBlank String message)
    {
        super(message);
    }

    public BusinessException(@NotBlank String message, @NotNull Throwable cause) {
        super(message, cause);
    }

    public BusinessException(@NotNull Throwable cause) {
        super(cause);
    }

}
