package com.heihei.bookrecommendsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("com.heihei.bookrecommendsystem.dao")
public class WebMainApplication
{
    public static void main( String[] args ) {
        SpringApplication.run(WebMainApplication.class);
    }
}
