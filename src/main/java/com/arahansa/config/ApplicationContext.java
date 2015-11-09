package com.arahansa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
// 토비의 스프링 Vol2. 203 페이지에 보면 설정클래스가 다시 등록이 되면 곤란하다. 제외하자. 
@ComponentScan(basePackages = { "com.arahansa" }, excludeFilters=@Filter(Configuration.class))
@PropertySource({"classpath:application.properties"})
public class ApplicationContext {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer pspc() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
