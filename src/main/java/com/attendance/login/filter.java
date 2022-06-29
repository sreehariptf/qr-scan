
package com.attendance.login;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

public class Filter {

@Bean
public FilterRegistrationBean simpleCorsFilter() {
UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
CorsConfiguration config = new CorsConfiguration();
config.setAllowCredentials(true);
// *** URL below needs to match the Vue client URL and port ***
config.setAllowedOrigins(Collections.singletonList("https://62bc15011f0688046c766f42--super-gumption-313743.netlify.app/"));
config.setAllowedMethods(Collections.singletonList("*"));
config.setAllowedHeaders(Collections.singletonList("*"));
source.registerCorsConfiguration("/**", config);
FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter((CorsConfigurationSource) source));
bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
return bean;
}

}
