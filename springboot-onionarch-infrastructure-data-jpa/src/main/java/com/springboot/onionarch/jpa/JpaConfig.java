package com.springboot.onionarch.jpa;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = JpaConfig.class)
public class JpaConfig {
}
