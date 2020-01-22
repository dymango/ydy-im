package com.dyman.im.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T>{

    private String message;
    private int code;
    public T body;

    public Result(String message, int code)
    {
        this.message = message;
        this.code = code;
    }

}
