package com.example.userservice.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//声明是一个配置类
@Configuration
public class Config {





    //注册到spring容器中
    @Bean
    public CorsFilter corsFilter(){
        //初始化cors配置对象
        CorsConfiguration configuration = new CorsConfiguration();
        //配置需要跨域的地址,如果要携带cookie,不能写*，*：代表所有域名都可以跨域访问
        configuration.addAllowedOriginPattern("*");
        //配置是否允许带cookie
        configuration.setAllowCredentials(true);
        //配置所要携带的请求方法，*代表所有的请求的方法：Get put post delete
        configuration.addAllowedMethod("*");
        //配置所要携带的头信息
        configuration.addAllowedHeader("*");

        //初始化cors配置源对象，添加映射路径，拦截一些请求
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource=new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",configuration);

        //返回corsFilter实例，参数：cors配置源对象
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}

