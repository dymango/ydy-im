package com.dyman.im;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

@SpringBootApplication
@MapperScan("com.dyman.im.mapper")
public class ImApplication {

    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(ImApplication.class, args);
        EnumCache.init();
    }

}
