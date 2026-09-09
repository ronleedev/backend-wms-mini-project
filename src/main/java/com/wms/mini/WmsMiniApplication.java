package com.wms.mini;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wms.mini.mapper")
public class WmsMiniApplication {
    public static void main(String[] args) {
        SpringApplication.run(WmsMiniApplication.class, args);
    }
}
