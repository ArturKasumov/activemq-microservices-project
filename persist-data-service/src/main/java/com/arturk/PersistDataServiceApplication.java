package com.arturk;

import com.arturk.common.service.ExceptionHandlerResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ExceptionHandlerResolver.class)
public class PersistDataServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersistDataServiceApplication.class, args);
    }
}