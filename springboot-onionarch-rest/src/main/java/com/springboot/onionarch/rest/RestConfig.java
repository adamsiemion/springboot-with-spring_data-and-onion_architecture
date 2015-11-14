package com.springboot.onionarch.rest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = RestModule.class)
public class RestConfig {
}
