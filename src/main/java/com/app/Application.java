package com.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.app.config.DispatcherConfig;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.app.controller"})
@Import({WebAppInitializer.class, DispatcherConfig.class})
public class Application {

}
