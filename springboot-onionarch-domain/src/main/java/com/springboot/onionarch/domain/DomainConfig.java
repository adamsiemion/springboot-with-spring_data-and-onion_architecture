package com.springboot.onionarch.domain;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = DomainModule.class)
public class DomainConfig {
}
