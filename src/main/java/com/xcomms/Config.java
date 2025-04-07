package com.xcomms;


import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
// import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class Config implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry){
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*");
    }

    // @Bean
    // public FilterRegistrationBean<RequestFilter> loggingFilter() {
    //     FilterRegistrationBean<RequestFilter> registrationBean = new FilterRegistrationBean<>();
    //     registrationBean.setFilter(new RequestFilter());
    //     registrationBean.addUrlPatterns("/*"); // Apply to all requests

    //     return registrationBean;
    // }

}
