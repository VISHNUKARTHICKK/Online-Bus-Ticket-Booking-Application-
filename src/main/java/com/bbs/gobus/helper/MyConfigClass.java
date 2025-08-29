package com.bbs.gobus.helper;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfigClass {

    @Bean
    Random random()
    {
        return new Random();
    }
}
