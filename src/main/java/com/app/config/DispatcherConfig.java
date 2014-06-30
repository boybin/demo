package com.app.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({ThymeleafConfig.class})
public class DispatcherConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");   
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
    }
    
    @Bean
    public MessageSource messageSource() {

      ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
      messageSource.setBasenames("classpath:messages/messages", "classpath:messages/validation");
      // if true, the key of the message will be displayed if the key is not
      // found, instead of throwing a NoSuchMessageException
      messageSource.setUseCodeAsDefaultMessage(true);
      messageSource.setDefaultEncoding("UTF-8");
      // # -1 : never reload, 0 always reload
      messageSource.setCacheSeconds(0);
      return messageSource;
    }
}
