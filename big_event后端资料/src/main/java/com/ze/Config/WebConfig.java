package com.ze.Config;

import com.ze.HandlerConfig.LoginHeandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration=配置类因为他也是一个配置类所以需要添加一个配置类的注解  自动注入才不会报错
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginHeandler loginHeandler;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 登录和注册不进行拦截
         */
       registry.addInterceptor(loginHeandler).excludePathPatterns("/user/login","/user/register");

    }
}
